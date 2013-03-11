/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecdlplay.gui;

import ecdlplay.data.Texts;
import ecdlplay.domain.Answer;
import ecdlplay.domain.GameEngine;
import ecdlplay.domain.GameEngineConstants;
import ecdlplay.domain.Player;
import ecdlplay.domain.Question;
import ecdlplay.utils.ImageLoader;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 * @author julio
 */
public class GGameCanvas extends CanvasBase{
    private TFont fntQuestion;
    private TFont fntAnswerNormal;
    private TFont fntDice;
    private Image[] dice;
    private Image[] players;
    private Image playerBright;
    private Image playerTurnBright;

    public GGameCanvas(GameEngine ge){
        super(ge);
    }
    
    @Override
    public void loadResources() {
        setBackground(ImageLoader.loadImageJAR("game_background.png"));
        loadFonts();
        loadDice();
        loadPlayers();
        loadGameButtons();
        loadAnswerComponents();
    }

    private void loadFonts() {
        fntQuestion = new TFont("futura.ttf", 18, Font.BOLD, 0xFFFFFF);
        fntAnswerNormal = new TFont("futura.ttf", 14, Font.PLAIN, 0xEED4F5);
        fntDice = new TFont("futura.ttf", 30, Font.BOLD, 0xFFFFFF);
    }

    private void loadDice() {
        dice = new Image[6];
        // Load Images
        for (int i = 0; i < dice.length; i++) {
            dice[i] = ImageLoader.loadImageJAR("game_dice_" + (i + 1) + ".png");
        }
    }
    
    private void loadPlayers() {
        // Create Players array
        players = new Image[4];
        // Load Images
        for (int i = 0; i < players.length; i++) {
            players[i] = ImageLoader.loadImageJAR("game_player_" + (i + 1) + ".png");
        }
        // Load Brights
        playerBright = ImageLoader.loadImageJAR("game_player_bright.png");
        playerTurnBright = ImageLoader.loadImageJAR("game_player_turn_bright.png");
    }
    
    private void loadGameButtons() {
        // Component Back
        addComponent(new Button(
                GameCanvasConstants.BUTTON_BACK,
                GameCanvasConstants.GAME_BUTTON_BACK_X,
                GameCanvasConstants.GAME_BUTTON_BACK_Y,
                ImageLoader.loadImageJAR("menu_button_back_1.png"),
                ImageLoader.loadImageJAR("menu_button_back_2.png")));
        // Component Help
        addComponent(new Button(
                GameCanvasConstants.BUTTON_HELP,
                GameCanvasConstants.MAIN_MENU_BUTTON_HELP_X,
                GameCanvasConstants.MAIN_MENU_BUTTON_HELP_Y,
                ImageLoader.loadImageJAR("menu_button_help.png"),
                ImageLoader.loadImageJAR("menu_button_help.png")));
        
        
    }
    
    private void loadAnswerComponents() {
        // Component Answers
        addComponent(new Checkbox(
                GameCanvasConstants.BUTTON_ANSWER1,
                GameCanvasConstants.ANSWER1_X,
                GameCanvasConstants.ANSWER1_Y,
                ImageLoader.loadImageJAR("game_answerbox_1.png"),
                ImageLoader.loadImageJAR("game_answerbox_2.png")));
        addComponent(new Checkbox(
                GameCanvasConstants.BUTTON_ANSWER2,
                GameCanvasConstants.ANSWER2_X,
                GameCanvasConstants.ANSWER2_Y,
                ImageLoader.loadImageJAR("game_answerbox_1.png"),
                ImageLoader.loadImageJAR("game_answerbox_2.png")));
        addComponent(new Checkbox(
                GameCanvasConstants.BUTTON_ANSWER3,
                GameCanvasConstants.ANSWER3_X,
                GameCanvasConstants.ANSWER3_Y,
                ImageLoader.loadImageJAR("game_answerbox_1.png"),
                ImageLoader.loadImageJAR("game_answerbox_2.png")));
    }
    
    @Override
    public void paint(Graphics g) {
        
        paintGame(g);
        
        switch(this.gameEngine.getState()){
            case GameEngineConstants.STATE_GAME_ANSWER:
                paintQuestion(g, gameEngine.getQuestion());
                paintAnswers(g);
                break;
            case GameEngineConstants.STATE_GAME_ANSWER_OK:
                paintMessage(g, Texts.getText(Texts.TEXT_RIGHT));
                break;
            case GameEngineConstants.STATE_GAME_ANSWER_FAIL:
                paintMessage(g, Texts.getText(Texts.TEXT_FAIL));
                break;
            case GameEngineConstants.STATE_GAME_BRAKE:
                paintMessage(g, Texts.getText(Texts.TEXT_BRAKES, String.valueOf(gameEngine.getTurn() + 1)));
                break;
            case GameEngineConstants.STATE_GAME_DICES:
            case GameEngineConstants.STATE_GAME_MOVING:
                paintDice(g);
                break; 
            case GameEngineConstants.STATE_GAME_WIN:
                paintMessage(g, Texts.getText(Texts.TEXT_WIN, String.valueOf(gameEngine.getTurn() + 1)));
                break;
                    
        }
    }
    
    private void paintGame(Graphics g) {
        Player playersArray[] = gameEngine.getPlayers();
        paintBackground(g);
        paintPlayers(g, playersArray);
        paintPlayersTurn(g, playersArray.length);
    }

    private void paintDice(Graphics g) {
        
        int numDice = gameEngine.getNumDice();
        // Remove any Clip
        resetClip(g);

        if (numDice <= 6) // Draw Dice
        {
            g.drawImage(dice[numDice - 1], GameCanvasConstants.DICE_X, GameCanvasConstants.DICE_Y, null);
        }


        // Draw Text
        fntDice.drawString(g, String.valueOf(numDice), 
                GameCanvasConstants.DICE_TEXT_X, 
                GameCanvasConstants.DICE_TEXT_Y, 
                TFont.BASELINE | TFont.HCENTER);
    }

    private void paintAnswers(Graphics g) {
        ArrayList<Answer> anwsers = gameEngine.getAnswers();
        // Paint Checkboxes
        paintComponents(g);

        // Paint Answers
        paintAnswer(g, 
                GameCanvasConstants.ANSWER1_X + GameCanvasConstants.ANSWERBOX_X, 
                GameCanvasConstants.ANSWER1_Y + GameCanvasConstants.ANSWERBOX_Y, 
                GameCanvasConstants.ANSWERBOX_WIDTH, 
                GameCanvasConstants.ANSWERBOX_HEIGHT, 
                anwsers.get(0));
        
        paintAnswer(g, 
                GameCanvasConstants.ANSWER2_X + GameCanvasConstants.ANSWERBOX_X, 
                GameCanvasConstants.ANSWER3_Y + GameCanvasConstants.ANSWERBOX_Y, 
                GameCanvasConstants.ANSWERBOX_WIDTH, 
                GameCanvasConstants.ANSWERBOX_HEIGHT, 
                anwsers.get(1));
        
        paintAnswer(g, 
                GameCanvasConstants.ANSWER3_X + GameCanvasConstants.ANSWERBOX_X, 
                GameCanvasConstants.ANSWER3_Y + GameCanvasConstants.ANSWERBOX_Y, 
                GameCanvasConstants.ANSWERBOX_WIDTH, 
                GameCanvasConstants.ANSWERBOX_HEIGHT, 
                anwsers.get(2));
    }

    private void paintAnswer(Graphics g, int x, int y, int width, int height, Answer answer) {
        // Remove any clip
        resetClip(g);
        //g.setColor(Color.RED);
        //g.fillRect(x, y, width, height);
        // Draw Text
        fntAnswerNormal.drawString(g, answer.getTexto(), x, y, width, height, TFont.JUSTIFY | TFont.TOP);
    }

    private void paintPlayersTurn(Graphics g, int numPlayers) {
        int x = GameCanvasConstants.PLAYERS_TURN_X;
        int y = GameCanvasConstants.PLAYERS_TURN_Y;

        // Reset clip
        resetClip(g);
        // Iterate Players
        for (int i = 0; i < numPlayers; i++) {
            if (gameEngine.getTurn() == i) {
                g.drawImage(playerTurnBright,
                        x - (playerTurnBright.getWidth(null) >> 2) + 3,
                        y - (playerTurnBright.getHeight(null) >> 2) + 2,
                        null);
            }

            // Paint Turn
            g.drawImage(players[i], x, y, null);
            // Increment next
            x += GameCanvasConstants.PLAYERS_TURN_GAP;
        }
    }

    private void paintPlayers(Graphics g, Player players[]) {
        for (Player p : players) {
            if (p.getNumPlayer() == gameEngine.getTurn()) {
                paintPlayer(g, p, gameEngine.playerRouteOffsetX, gameEngine.playerRouteOffsetY);
            } else {
                paintPlayer(g, p, 0, 0);
            }
        }

        // Paint Turn Player
        paintPlayer(g, players[gameEngine.getTurn()], gameEngine.playerRouteOffsetX, gameEngine.playerRouteOffsetY);
    }

    private void paintPlayer(Graphics g, Player player, int offsetX, int offsetY) {
        // Get Square
        int numSquare = player.getNumSquare();
        // Create Coords
        int x = GameCanvasConstants.SQUARES_COORD[ numSquare << 1] + offsetX;
        int y = GameCanvasConstants.SQUARES_COORD[(numSquare << 1) + 1] + offsetY;

        // Reset Clip
        resetClip(g);

        // Check Actual Turn
        if (player.getNumPlayer() == gameEngine.getTurn() && gameEngine.getState() != GameEngineConstants.STATE_GAME_MOVING) {
            g.drawImage(playerBright,
                    x - (playerBright.getWidth(null) >> 1),
                    y - (playerBright.getHeight(null) >> 1),
                    null);
        }

        // Paint
        g.drawImage(players[player.getNumPlayer()],
                x - (players[player.getNumPlayer()].getWidth(null) >> 1),
                y - (players[player.getNumPlayer()].getHeight(null) >> 1),
                null);
    }
    
    private void paintQuestion(Graphics g, Question question){
        paintMessage(g, question.getTexto());
        
        if (question.getImagen() != null && question.getImagen().getImagen() != null){
            
            // Show Image Button
            addComponent(new Button(
                GameCanvasConstants.BUTTON_SHOW_IMAGE,
                GameCanvasConstants.GAME_BUTTON_SHOW_IMAGE_X,
                GameCanvasConstants.GAME_BUTTON_SHOW_IMAGE_Y,                
                ImageLoader.loadImageJAR("game_lupa.png"),
                ImageLoader.loadImageJAR("game_lupa.png")));
            
            Image image = ImageLoader.loadImage(question.getImagen().getImagen());
            
            FloatImage win = FloatImage.getInstance();
            win.setSize(image.getWidth(null), image.getHeight(null));
            
            win.setImage(image);            
        }
            
    }
    
    private void paintMessage(Graphics g, String text){
        
        // Remove any clip
        resetClip(g);
        // Get Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        // Set Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Paint Question
        fntQuestion.drawString(g2d, text, 
                GameCanvasConstants.QUESTION_X, 
                GameCanvasConstants.QUESTION_Y, 
                GameCanvasConstants.QUESTION_WIDTH, 
                GameCanvasConstants.QUESTION_HEIGHT, 
                TFont.VCENTER | TFont.HCENTER);
    }
}
