import java.util.Scanner;

/**
 * The game class uses an MDP to explore a GridWorld.
 * 
 * @author Mitch Parry
 * @version 2014-03-28
 * 
 */
public class Game
{
    private MarkovDecisionProcess mdp;
    private Player player;

    /**
     * Constructor initializes the MDP and player.
     * 
     * @param mdp
     *            the MDP
     * @param player
     *            the player
     */
    public Game(MarkovDecisionProcess mdp, Player player)
    {
        this.mdp = mdp;
        this.player = player;
    }

    /**
     * Plays the game by repeatedly querying the player for an action until it
     * reaches a terminal state, accumulating points along the way.
     * 
     * @return the final score for the game.
     */
    public double play()
    {
        State current = mdp.getCurrent();
        double score = current.reward();
        int t = 1;
        while (!mdp.getCurrent().isTerminal())
        {
            System.out.println("Current score: " + score);
            String action = player.play((MarkovDecisionProcess) mdp.clone());
            if (action == null)
            {
                break;
            }
            if (!mdp.getActions().contains(action))
            {
                continue;
            }
            mdp.takeAction(action);
            current = mdp.getCurrent();
            score += Math.pow(mdp.getGamma(), t) * current.reward();
            t++;
        }
        return score;
    }

    /**
     * This is the main game program with a human player. Run this to try out a
     * random GridWorld game.
     * 
     * @param args
     *            not used.
     */
    public static void main(String[] args)
    {
        String world =
            GridWorld.createRandomGridWorld(10, 10, 0, 2, 10, 1, 1.0);
        MarkovDecisionProcess mdp =
            new MarkovDecisionProcess(new Scanner(world));
        Player p = new HumanPlayer("Player1");
        Game game = new Game(mdp, p);
        double score = game.play();
        GridWorld.display(mdp.getStates(), mdp.getCurrent());
        System.out.println("Final Score: " + score);
    }
}
