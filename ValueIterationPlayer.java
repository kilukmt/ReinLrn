import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An agent that uses value iteration to play the game.
 * 
 * @author Mitch Parry
 * @version 2014-03-28
 * 
 */
public class ValueIterationPlayer extends Player
{
    /**
     * The constructor takes the name.
     * 
     * @param name
     *            the name of the player.
     */
    public ValueIterationPlayer(String name)
    {
        super(name);
    }

    /**
     * returns neighbors of current state as well as the direction from current state
     * @param sState current state
     * @param mdp
     * @return neighbors and directions from current state
     */
    private HashMap<State, String> getNeighbors(State sState, MarkovDecisionProcess mdp)
    {
    	HashMap<State, String> neighbors = new HashMap<State, String>();
    	State eState;
    	for (int i = 0; i < mdp.getStates().size(); i++){
    		eState = mdp.getStates().get(i);
    		for(String direction : mdp.getActions()){
    			double chance = mdp.transProb(sState, direction, eState);
    			if (chance > 0.0)
    			{
    				neighbors.put(eState, direction);
    			}
    		}
    	}
    	return neighbors;
    }
    
    /**
     * Returns a list of terminal States
     * @param mdp
     * @return List<State> terminals
     */
    private List<State> getTerminalStates(MarkovDecisionProcess mdp)
    {
    	List<State> terminals = new ArrayList<State>();
    	for (State s : mdp.getStates()){
    		if(s.isTerminal()){
    			terminals.add(s);
    		}
    	}
    	return terminals;
    }
    
    /**
     * Returns the goal state with a reward of 100.
     * @param mdp
     * @return the goal state
     */
    private State getGoalState(MarkovDecisionProcess mdp)
    {
    	for (State s : mdp.getStates()){
    		if (s.reward() == 100){
    			return s;
    		}
    	}
		return null;
    }
    
    /**
     * Returns the adjacent state with greatest Reward and the direction from current state
     * @param currState
     * @param neighbors
     * @returns neighbor with greatest reward and direction
     */
    private HashMap.Entry<State, String> getOptimalNeighbor(State currState, HashMap<State, String> neighbors)
    {
    	State best;
    	double maxReward = 0;
    	HashMap.Entry<State, String> bestNeighbor = null;
    	for (HashMap.Entry<State,String> adjState : neighbors.entrySet())
    	{
    		if (adjState.getKey().reward() > maxReward)
    		{
    			maxReward = adjState.getKey().reward() + currState.reward();
    			bestNeighbor = adjState;
    		}
    	}
    	
    	return bestNeighbor;
    }
    
    /**
     * determines current best policy of State Action pairs
     * @param mdp - MarkovDecisionProcess
     * @return the policy for current state
     */
    private HashMap<State, String> getBackup(MarkovDecisionProcess mdp)
    {
    	HashMap<State, String> policy = new HashMap<State, String>();
    	State goalState = getGoalState(mdp);
    	
    	for (State s : mdp.getStates())
    	{
    		HashMap<State, String> neighbors = getNeighbors(s, mdp);
    		HashMap.Entry<State, String> bestNeighbor = getOptimalNeighbor(s, neighbors);
    		policy.put(s, bestNeighbor.getValue());
    	}
    	return policy;
    }
    

    /**
     * converges for optimal policy
     * @param mdp
     * @return converged optimal policy
     */
    private HashMap<State, String> getOptimalPolicy(MarkovDecisionProcess mdp)
    {
    	
    }

    /**
     * Plays the game using value iteration to precompute the policy and then
     * applying the policy in future moves. The Abstract Player class has
     * 
     * @param mdp MarkovDecisonProcess
     *            the MDP.
     * @return the desired action.
     */
    public String play(MarkovDecisionProcess mdp)
    {
        // final double EPS = 1e-3;

        // if the utility hasn't been computed yet
        // use value iteration to compute the utility of each state.
        // use the utility to determine the optimal policy.
        // endif
        //
        // return the action for the current state from the policy.
        return "N";
    }
}
