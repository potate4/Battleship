package application;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class Controller {
    
    @FXML
    private GridPane player1ship;
    @FXML
    private GridPane player2ship;
    @FXML
    private GridPane enemy1;
    @FXML
    private GridPane enemy2;
    @FXML
    private Label labele1;
    @FXML
    private Label labele2;
    @FXML
    private Label labelp1;
    @FXML
    private Label labelp2;
    
    private boolean isHorizontalPlacement = false;
    boolean hit = false;
    boolean  moved = false;
    boolean shown = false;
    String instructions = "";

    
    private int[] shipSizes = {3, 2, 2, 1, 1, 1}; // Sizes of the ships
    int totalShips = shipSizes.length;
    
    public Ship[] player1ShipList = new Ship[totalShips];
    public Ship[] player2ShipList = new Ship[totalShips];
   
    int placedShipsPlayer1 = 0;
    int placedShipsPlayer2 = 0;
    
    int player1Health = 10;
    int player2Health = 10;
    
    boolean winnerDecided = false;  
    boolean playerTurn1 = true;
    boolean allShipsPlaced = false;

    @FXML
    private void initialize() {
        // Loop through the children of the first GridPane to find buttons
        enemy2.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setOnKeyPressed(event -> {
                    if (event.getText().equalsIgnoreCase("h")) {
                        isHorizontalPlacement = true; // Set horizontal placement
                        System.out.println("Hor");
                    } else if (event.getText().equalsIgnoreCase("v")) {
                        isHorizontalPlacement = false; // Set vertical placement
                        System.out.println("Ver");
                    }
                });
                
                // Then set the onAction event
                button.setOnAction(this::onclick);
            }
        });
        
        // Loop through the children of the second GridPane to find buttons
        player1ship.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setOnKeyPressed(event -> {
                    if (event.getText().equalsIgnoreCase("h")) {
                        isHorizontalPlacement = true; // Set horizontal placement
                        System.out.println("Hor");
                    } else if (event.getText().equalsIgnoreCase("v")) {
                        isHorizontalPlacement = false; // Set vertical placement
                        System.out.println("Ver");
                    }
                });
                
                // Then set the onAction event
                button.setOnAction(this::onclick);
            }
        });
        
        
        enemy1.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setOnKeyPressed(event -> {
                    if (event.getText().equalsIgnoreCase("h")) {
                        isHorizontalPlacement = true; // Set horizontal placement
                        System.out.println("Hor");
                    } else if (event.getText().equalsIgnoreCase("v")) {
                        isHorizontalPlacement = false; // Set vertical placement
                        System.out.println("Ver");
                    }
                });
                
                // Then set the onAction event
                button.setOnAction(this::onclick);
            }
        });
        
        // Loop through the children of the second GridPane to find buttons
        player2ship.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setOnKeyPressed(event -> {
                    if (event.getText().equalsIgnoreCase("h")) {
                        isHorizontalPlacement = true; // Set horizontal placement
                        System.out.println("Hor");
                    } else if (event.getText().equalsIgnoreCase("v")) {
                        isHorizontalPlacement = false; // Set vertical placement
                        System.out.println("Ver");
                    }
                });
                
                // Then set the onAction event
                button.setOnAction(this::onclick);
            }
        });
        
        instructions = "There are 4 boards, for each player there's one to place ships and another to attack enemies\n"
        		+ "1. Player 1 must place the ships by clicking a cell of the Player 1 Board.\n"
        		+ "To place the ships HORIZONTALLY -  press H, to revert back to VERTICAL - press V\n"
        					+ "2. After Player 1 has finished plaching all the ships, Player 2 must proceed to place their ships.\n"
        					+ "3. Now the players take turns to attack the opponents by shooting on the enemy boards\n"
        					+ "4. Player 1 starts attacking by shooting at Enemy Board for Player 1 - (this corresponds to the player 2 board)\n"
        					+ "5. If it's a hit, the player can click on one of their intact ships and move them\n"
        					+ " by one square by pressing l(left), r(right), d(down), u(up), n(no movement)\n"
        					+ "6. The turn will then be given to the next player when the SWITCH button is clicked\n"
        					+ "7. Whoever destroys all the opponent ships first, wins";
        
        if(!shown)
        {
            showAlert("RULES", instructions);
            shown = true;
        }
        System.out.print(instructions);
        handleVisibility();
    }
    
    public void onclick(ActionEvent event) {
     
        if(!allShipsPlaced)
        	handleShipPlacement(event);
        else if(!winnerDecided)
        	handleAttack(event);
        
       
    }
        
    private void placeShip(GridPane grid, int row, int col, int size, boolean h) {
    	
    	int p1 = 0, p2 = 0;
    	Ship ship = new Ship(size); 
    	ship.size = size ;
    	
    	if(playerTurn1)
    	{	
    		player1ShipList[placedShipsPlayer1] = ship;
    	}
    	else player2ShipList[placedShipsPlayer2] = ship;
    	
        for (int i = 0; i < size; i++) {
        	if(playerTurn1)
        	{
            	player1ShipList[placedShipsPlayer1].rowCoordinates[p1] = row;
            	player1ShipList[placedShipsPlayer1].colCoordinates[p1] = col;   
                p1++;
        	}
        	
        	else
        	{
            	player2ShipList[placedShipsPlayer2].rowCoordinates[p2] = row;
            	player2ShipList[placedShipsPlayer2].colCoordinates[p2] = col;
            	p2++;
        	}
        	
            Button button = getButtonAt(grid, row, col);            
           // System.out.println(h);
            button.setStyle("-fx-background-color: #000000"); // Change color to indicate ship placement
            String shipName = String.valueOf(size);
            button.setText(shipName);
            if (h) {
                col++; // Move to the next column for horizontal placement
            } else {
                row++; // Move to the next row for vertical placement
            }
        }
    }

    private Button getButtonAt(GridPane gridPane, int row, int col) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row && node instanceof Button) {
                return (Button) node;
            }
        }
        return null;
    }
    
    private boolean canPlaceShip(GridPane grid, int row, int col, int size, boolean h) {
    	
      //  System.out.println("Can place ship stuff");
    	
        if (h && col + size > grid.getColumnCount()) {
            System.out.println(col + size);
            System.out.println(grid.getColumnCount());
            return false; // Ship goes out of bounds horizontally
        }
        if (!h && row + size > grid.getRowCount()) {
        	System.out.println(row + size);
            System.out.println(grid.getRowCount());
            return false; // Ship goes out of bounds vertically
        }
        
        for (int i = 0; i < size; i++) {
            Button button = getButtonAt(grid, row, col);
            //button.setText("B");
            if (!button.getText().isEmpty()) {
                return false; // Ship overlaps with another ship
            }
            if (h) {
                col++; // Move to the next column for horizontal placement
            } else {
                row++; // Move to the next row for vertical placement
            }
        }
        return true; // Ship can be placed at the specified position
    }

    private void handleShipPlacement(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        GridPane grid = (GridPane) clickedButton.getParent();
        int row = GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton);
        
//        System.out.println("Same col " + col);
//        System.out.println("Same col " + row);
        
        String shipName = grid.getId();
        //System.out.println("Ship : " + shipName);
        
        
       
        
        if(!allShipsPlaced && (playerTurn1 == true && shipName.equals("player1ship")) || (playerTurn1 == false && shipName.equals("player2ship")))
        {
	        int shipSize = 0;
	        
	        if(playerTurn1 && placedShipsPlayer1 < totalShips)
	        {
	        	shipSize = shipSizes[placedShipsPlayer1];
	        }
	        if(!playerTurn1 && placedShipsPlayer2 < totalShips)
	        {
	        	shipSize = shipSizes[placedShipsPlayer2];
	        }
	        
	        
	        boolean h = canPlaceShip(grid, row, col, shipSize, isHorizontalPlacement);
	       // System.out.println("can place " + h);
	        
	        if(h == true)
	        {
	        	placeShip(grid, row, col, shipSize, isHorizontalPlacement);
	        	if(playerTurn1 == true && placedShipsPlayer1 < totalShips) placedShipsPlayer1++;
	        	else if(playerTurn1 == false && placedShipsPlayer2 < totalShips)placedShipsPlayer2++;
	        	
	        	//System.out.println("Ships Placed - " + placedShipsPlayer1 + ", " + placedShipsPlayer2);
	        }
	        if(placedShipsPlayer1 >= totalShips)
	        {
	        		playerTurn1 = false;
	        		//showAlert("RULES", "All ships placed for PLAYER 1\nPlayer 2 will now place ships");
	        }
	        if(placedShipsPlayer2 >= totalShips) allShipsPlaced = true;
	        if(allShipsPlaced) playerTurn1 = true;
        }
        else System.out.println("wrong ship");
        
        if(allShipsPlaced)
        {
        	showAlert("info", "All ships Placed\nPlayer 1 can now start attacking through enemy board for player 1");
        	handleVisibility();
        }

    }
 
    private boolean attackEnemy (GridPane gridP, GridPane gridE, int row, int col) {
    	  Button buttonE = getButtonAt(gridE, row, col);
    	  Button buttonP = getButtonAt(gridP, row, col);

          if (buttonP.getText().isEmpty()) {
              System.out.println("Miss");
              buttonE.setStyle("-fx-background-color: #FFFFFF"); // Change color to indicate ship placement
              buttonE.setText("X");
              return false;
          }
          else 
          {
        	  if(buttonP.getText().equals("1") || buttonP.getText().equals("2") || buttonP.getText().equals("3"))
        	  {
        		  buttonP.setStyle("-fx-background-color: #FF0000"); // Change color to indicate ship placement
                  buttonP.setText("X");
      
        		  buttonE.setStyle("-fx-background-color: #FF0000"); // Change color to indicate ship placement
                  buttonE.setText("X"); 
                  
                  if(playerTurn1) player2Health--;
                  else player1Health--;
                  
                  if(player1Health == 0)
                  {
                	  winnerDecided = true;
                	  System.out.println("Winner = player 2");
                	  showAlert("Winner", "Player2");
                	  closeApp();
                  }
                  else if(player2Health == 0)
                  {
                	  winnerDecided = true;
                	  System.out.println("Winner = player 1");
                	  showAlert("Winner", "Player1");
                	  closeApp();
                  }
                  return true;
        	  }
        	  return false;
          }
    }
    
    public void handleVisibility()
    {
        if(playerTurn1)
        {
        	player2ship.setVisible(false);
        	enemy1.setVisible(false);
        	
        	player1ship.setVisible(true);
        	enemy2.setVisible(true);
        	
        	labelp2.setVisible(false);
        	labele2.setVisible(false);
        	
        	labelp1.setVisible(true);
        	labele1.setVisible(true);

        }
        else
        {
        	player1ship.setVisible(false);
        	enemy2.setVisible(false);
        	
        	player2ship.setVisible(true);
        	enemy1.setVisible(true);
        	
        	
        	labelp2.setVisible(true);
        	labele2.setVisible(true);
        	
        	labelp1.setVisible(false);
        	labele1.setVisible(false);
        }
    }
    
    public void handleAttack(ActionEvent event)
    {
        Button clickedButton = (Button) event.getSource();
        GridPane gridE = (GridPane) clickedButton.getParent();
        int row = GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton);
        String shipName = gridE.getId();
        boolean intact = true;
        
        //handleVisibility();
               
        if(hit && playerTurn1 && shipName.equals("player1ship"))
        {
            int idx = getShipIndex(1, row, col);
            intact = validMovement(1, idx, row, col, 0, 0);
            
        	Button button = getButtonAt(player1ship, row, col);
        	
        	if(!intact || button.getText().isEmpty())
        	{
        		moved = true;
        		playerTurn1 = false;
        		hit = false;
        	}
        	else if(intact && !button.getText().isEmpty())
        	{
        	
	        	System.out.println("Press L, R, D, U to move the selected piece or Press N to skip\n");
	        	clickedButton.setOnKeyPressed(e -> {
	                 if (!moved && e.getText().equalsIgnoreCase("l")) {
	                     System.out.println("left");
	                    // playerTurn1 = false;
	                 	 moveIntact(1, row, col, 0, -1);
	                
	                 } else if (!moved && e.getText().equalsIgnoreCase("r")) {
	                     System.out.println("r");
	                    // playerTurn1 = false;
	                 	 moveIntact(1, row, col, 0, 1);
	                 }
	                 else if (!moved && e.getText().equalsIgnoreCase("u")) {
	                 	 moveIntact(1, row, col, -1, 0);
	                 	// playerTurn1 = false;
	                     System.out.println("u");
	                 	 //moved = true;
	
	                 }
	                 else if (!moved && e.getText().equalsIgnoreCase("d")) {
	                	 //playerTurn1 = false;
	                 	 moveIntact(1, row, col, 1, 0);
	                     System.out.println("d");
	                 
	                 }
	                 
	                 else if (!moved && e.getText().equalsIgnoreCase("n")) {
	                	 playerTurn1 = false;
	                     System.out.println("n");
	                     moved = true;
	             		 hit = false;
	                 }
	             });
	        	
	        	playerTurn1 = false;
        	}
        	
        }
        	
        
        else if(hit && !playerTurn1 && shipName.equals("player2ship"))
        {
        	Button button = getButtonAt(player2ship, row, col);
        	int idx = getShipIndex(2, row, col);
            intact = validMovement(2, idx, row, col, 0, 0);
            
         	if(!intact || button.getText().isEmpty())
        	{
        		moved = true;
        		playerTurn1 = true;
        		hit = false;
        	}
         	else if(intact && !button.getText().isEmpty())
        	{
	        	System.out.println("Press L, R, D, U to move the selected piece or Press N to skip\n");
	        	clickedButton.setOnKeyPressed(e -> {
	                 if (!moved && e.getText().equalsIgnoreCase("l")) {
	                     System.out.println("left");
	                 	 moveIntact(2, row, col, 0, -1);
	                 	//playerTurn1 = true;
	                 
	                 } else if (!moved && e.getText().equalsIgnoreCase("r")) {
	                     System.out.println("r");
	                     //playerTurn1 = true;
	                 	 moveIntact(2, row, col, 0, 1);
	                 }
	                 else if (!moved && e.getText().equalsIgnoreCase("u")) {
	                 	 moveIntact(2, row, col, -1, 0);
	                 	//playerTurn1 = true;
	                     System.out.println("u");
	                 }
	                 else if (!moved && e.getText().equalsIgnoreCase("d")) {
	                 	 moveIntact(2, row, col, 1, 0);
	                 	//playerTurn1 = true;
	                     System.out.println("d");
	                 }
	                 else if (!moved && e.getText().equalsIgnoreCase("n")) {
	                 	 
	                     System.out.println("n");
	                     playerTurn1 = true;
	                     moved = true;
	             		 hit = false;
	                 }
	             });
	        	playerTurn1 = true;
        	}
        } 
        
        else if(!hit && playerTurn1 && shipName.equals("enemy2"))
        {
        	hit  = attackEnemy(player2ship ,gridE, row, col);
        	moved = false;
        	if(hit == false)
        		playerTurn1 = false;
        	else
            	System.out.println("Hit\nClick on an intact Ship on your Player Board. Selecting unintact ship or a blank cell will skip the move.");

        }
        else if(!hit && !playerTurn1 && shipName.equals("enemy1"))
        {
        	hit = attackEnemy(player1ship, gridE, row, col);
        	moved = false;
        	if(hit == false)
        		playerTurn1 = true;
        	else  System.out.println("Hit\nClick on an intact Ship on your Player Board. Selecting unintact ship or a blank cell will skip the move.");

        }
        
    }
    
    
    public void moveIntact(int player, int row, int col, int x, int y)
    {
    	int index = getShipIndex(player, row, col);
    	if(validMovement(player, index, row, col, x, y))
    	{
    		moved = true;
    		hit = false;
    		move(player, index, row, col, x, y);
    	}
    	
    }
    
    public boolean validMovement(int player, int index, int row, int col, int x, int y)
    {
    	if(player == 1)
    	{
        	Button button = getButtonAt(player1ship, row, col);
        	if(button.getText().isEmpty()) return false;

    		for(int i = 0; i < player1ShipList[index].size; i++)
    		{
    			int r = player1ShipList[index].rowCoordinates[i];
    			int c = player1ShipList[index].colCoordinates[i];
    			button = getButtonAt(player1ship, r, c);
    			if(button.getText().equals("X")) return false;
    			
    			r = r + x;
    			c = c + y;
    			if(r < 0 || c < 0 || r > 8 || c > 8) return false;
    			
    			button = getButtonAt(player1ship, r, c);
    			
    			if(!(button.getText().isEmpty() || getShipIndex(player, r, c) == index )) return false;
    		}
    	}
    	else if(player == 2)
    	{
    		Button button = getButtonAt(player2ship, row, col);
        	if(button.getText().isEmpty()) return false;

    		for(int i = 0; i < player2ShipList[index].size; i++)
    		{
    			int r = player2ShipList[index].rowCoordinates[i];
    			int c = player2ShipList[index].colCoordinates[i];
    			button = getButtonAt(player2ship, r, c);
    			if(button.getText().equals("X")) return false;
    			
    			r = r + x;
    			c = c + y;
    			if(r < 0 || c < 0 || r > 8 || c > 8) return false;
    			
    			button = getButtonAt(player2ship, r, c);
    			if(!(button.getText().isEmpty() || getShipIndex(player, r, c) == index )) return false;
    		}
    	}
		return true;
    }
    public void move(int player,int index, int row, int col, int x, int y)
    {
    	if(player == 1)
    	{
        	Button button = getButtonAt(player1ship, row, col);

    		for(int i = 0; i < player1ShipList[index].size; i++)
    		{
    			int r = player1ShipList[index].rowCoordinates[i];
    			int c = player1ShipList[index].colCoordinates[i];
    			button = getButtonAt(player1ship, r, c);
    			
    			button.setText("");
    			button.setStyle("-fx-background-color: #f4f4f4");
    			
    		}
    		for(int i = 0; i < player1ShipList[index].size; i++)
    		{
    			int r = player1ShipList[index].rowCoordinates[i];
    			int c = player1ShipList[index].colCoordinates[i];
    		
    			
    			r = r + x;
    			c = c + y;
    			
    			button = getButtonAt(player1ship, r, c);
    			
    			button.setText(String.valueOf(player1ShipList[index].size));
    			button.setStyle("-fx-background-color: #000000");	
    			
    			player1ShipList[index].rowCoordinates[i] = r;
    			player1ShipList[index].colCoordinates[i] = c;
    		}
    	}
    	else if(player == 2)
    	{
        	Button button = getButtonAt(player2ship, row, col);

    		for(int i = 0; i < player2ShipList[index].size; i++)
    		{
    			int r = player2ShipList[index].rowCoordinates[i];
    			int c = player2ShipList[index].colCoordinates[i];
    			button = getButtonAt(player2ship, r, c);
    			
    			button.setText("");
    			button.setStyle("-fx-background-color: #f4f4f4");
    			
    		}
    		
    		for(int i = 0; i < player2ShipList[index].size; i++)
    		{
    			int r = player2ShipList[index].rowCoordinates[i];
    			int c = player2ShipList[index].colCoordinates[i];
    			button = getButtonAt(player2ship, r, c);
    			
    			
    			r = r + x;
    			c = c + y;
    			button = getButtonAt(player2ship, r, c);
    			
    			button.setText(String.valueOf(player2ShipList[index].size));
    			button.setStyle("-fx-background-color: #000000");
    			
    			player2ShipList[index].rowCoordinates[i] = r;
    			player2ShipList[index].colCoordinates[i] = c;
    		}
    	}
    }
    public int getShipIndex(int player, int row, int col)
    {
    	int index = 0;
    	
    	if(player == 1)
    	{
    		for(int i = 0; i < player1ShipList.length; i++)
    		{
    			for(int j = 0; j < player1ShipList[i].size; j++)
    			{
    				if(player1ShipList[i].rowCoordinates[j] == row && player1ShipList[i].colCoordinates[j] == col)
    				{
    					index = i;
    					break;
    				}
    			}
    		}
    	}
    	else if(player == 2)
    	{
    		for(int i = 0; i < player2ShipList.length; i++)
    		{
    			for(int j = 0; j < player2ShipList[i].size; j++)
    			{
    				if(player2ShipList[i].rowCoordinates[j] == row && player2ShipList[i].colCoordinates[j] == col)
    				{
    					index = i;
    					break;
    				}
    			}
    		}
    	}
    	return index;
    }
    public void printInfo(int player)
    {
    	if(player  == 1)
    	{
        	for(int i = 0; i < player1ShipList.length; i++)
        	{
       		 System.out.println("player 1 size = " + player1ShipList[i].size);

        		for(int j = 0; j < player1ShipList[i].size; j++)
        		{	
                    System.out.print("r " + player1ShipList[i].rowCoordinates[j]);
                    System.out.println(", c " + player1ShipList[i].colCoordinates[j]);
        		}
        		System.out.print("\n");
        	}
    	       
    	}
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Get the dialog pane
        DialogPane dialogPane = alert.getDialogPane();

        // Set the preferred size (width and height)
        dialogPane.setPrefWidth(500);
        dialogPane.setPrefHeight(400);

        // Apply any other customization as needed

        alert.showAndWait();
    }
    
    public void closeApp()
	{
		System.out.println("App is closing in 2 seconds"); // added this line so the answers can be checked properly before the app exits
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished( event -> Platform.exit());
		delay.play();
	}
}

