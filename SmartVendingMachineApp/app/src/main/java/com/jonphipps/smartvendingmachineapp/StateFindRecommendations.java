package com.jonphipps.smartvendingmachineapp;

import java.io.Serializable;
import java.util.ArrayList;

public class StateFindRecommendations extends State implements Serializable { //Class to represent the find recommendation state of the vending machine
    public StateFindRecommendations(Dispenser context) { super(context);}

    private ArrayList<Integer> recommendationIDs = new ArrayList<>(); //List to hold the IDs of the suggested items
    private ArrayList<Integer> selectedItem = new ArrayList<>(); //List to hold the IDs of the selected toppings
    private int[][] itemToUserMatrix; //2D array for the item to user matrix

    private void createItemToUserMatrix(){ //Function to create the item to user matrix by using sales data from the database
        ArrayList<OrderRecordModel> toppingOrderData = context.getToppingSalesData();
        int maxUserID = 0;
        int maxItemID = 0;
        OrderRecordModel currentRecord;

        for(int position = 0; position < toppingOrderData.size(); position++){
            currentRecord = toppingOrderData.get(position);
            if(currentRecord.getItemID() > maxItemID) maxItemID = currentRecord.getItemID();
            if(currentRecord.getUserID() > maxUserID) maxUserID = currentRecord.getUserID();
        }

        itemToUserMatrix = new int[maxItemID+1][maxUserID+1];

        for(int position = 0; position < toppingOrderData.size(); position++){
            currentRecord = toppingOrderData.get(position);
            itemToUserMatrix[currentRecord.getItemID()][currentRecord.getUserID()] = 1;
        }
    }

    private int dotProduct(int item1, int item2){ //Function to calculate the dot product between 2 vectors
        int result = 0;
        for(int index = 0; index < itemToUserMatrix[item1].length; index++){
            result = result + (itemToUserMatrix[item1][index]*itemToUserMatrix[item2][index]);
        }
        return result;
    }

    private double lengthOfItemVector(int item){ //Function to calculate the length of a vector
        double lengthSquared = 0;
        for(int i = 0; i < itemToUserMatrix[item].length; i++){
            lengthSquared = lengthSquared + Math.pow(itemToUserMatrix[item][i], 2);
        }
        return Math.sqrt(lengthSquared);
    }

    private double calculateSimilarlityScore(int item1, int item2){ //Function to calculate the similarity score using the cosine similarity equation
        return ((dotProduct(item1, item2)/(lengthOfItemVector(item1)*lengthOfItemVector(item2))));
    }

    private int suggestItem(int item){ //Function to find the most recommended item for a given source item
        double[] similarlityScores = new double[itemToUserMatrix.length];

        for(int i = 0; i < itemToUserMatrix.length; i++){
            if(item < itemToUserMatrix.length){
                similarlityScores[i] = calculateSimilarlityScore(item, i);
                if(Double.isNaN(similarlityScores[i])) similarlityScores[i] = 0;
            }
        }

        int index = 0;
        double highestScore = 0;
        for(int i = 0; i < similarlityScores.length; i++){
            if(similarlityScores[i] > highestScore && i != item && !selectedItem.contains(i)
                    && !recommendationIDs.contains(i) ) {
                index = i;
                highestScore = similarlityScores[i];
            }
        }

        return index;
    }

    public void findRecommendations(){ //State transition to find a recommendation for each topping selected
        createItemToUserMatrix();
        int noOfRecommendations = 0;


            for(int index = 0; index < context.getSizeOfSelectedToppings(); index++) {
                selectedItem.add(context.getSelectedTopping(index).getId());
            }

            for(int index = 0; index < context.getSizeOfSelectedToppings(); index++){
                recommendationIDs.add(suggestItem(context.getSelectedTopping(index).getId()));
            }

            ArrayList<InventoryModel> toppingInventory = context.getAllToppingsList();

            InventoryModel currentTopping;
            for(int index = 0; index < toppingInventory.size(); index++){
                currentTopping = toppingInventory.get(index);
                if(recommendationIDs.contains(currentTopping.getId()) && currentTopping.getStock() > 0
                        && !context.checkToppingIsAdded(currentTopping)){
                    context.setRecommendation(toppingInventory.get(index));
                    noOfRecommendations++;
                }
            }

        if(noOfRecommendations > 0){
            context.setState(State.SELECT_RECOMMENDATIONS);
        }
        else{
            context.setState(State.PRODUCT_SUMMARY);
        }
    }
}




