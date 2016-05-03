import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An agent that uses value iteration to play the game.
 * 
 * @author Max Kiluk
 * @version 2016-29-4
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
     * Value Iteration method.
     * Iterates through all the states, finds the utility for each state until they converge.
     * @param mdp MarkovDecisionProcess mdp
     * @return HashMap<State, Double> converged state and utility pair
     */
    private HashMap<State, Double> valueIteration(MarkovDecisionProcess mdp)
    {
    	//initializes U0 to all zeros
    	HashMap<State, Double> maxUtility = new HashMap<State, Double>();
    	HashMap<String, Double> utils = new HashMap<String, Double>();
    	HashMap<State, Double> backupUtility = new HashMap<State, Double>();
    	
    	for(int i = 0; i < mdp.getStates().size(); i++){
    		maxUtility.put(mdp.getStates().get(i), 0.0);
    	}
    	
    	do{
    		backupUtility = maxUtility;
    		for(int i = 0; i < mdp.getStates().size(); i++)
    		{
    			//List<Double> sum = new ArrayList<Double>();
    			//Double currUtil = 0.0;
    			for (String direction : mdp.getActions())
    			{
    				double currUtil = 0.0;
    				for (State neighbor : getNeighbors(mdp.getStates().get(i), mdp))
    				{
    					currUtil += (backupUtility.get(neighbor) * mdp.transProb(mdp.getStates().get(i), direction, neighbor));
    				}
    				utils.put(direction, currUtil);
    			}
    			maxUtility.put(mdp.getStates().get(i), (Collections.max(utils.values())* mdp.getGamma()) + mdp.getStates().get(i).reward());
    		}
    	} while (getUtilityDelta(backupUtility, maxUtility) > Math.pow(10, -3));
    	return maxUtility;
    }
    
    
	/**
	 * Returns the expected reward/utility of a given state, as described in-
	 * 	AIMA: 17.2.1: The Bellman Equation. 
	 * @param state -The state to calculate the utility for. 
	 * @param mdp -The Markov Decision Process.
	 * @return state_utility -The utility/expected reward of a given state.
	 */
//	private Double getStateUtility(State state, MarkovDecisionProcess mdp) {
//		//Every state has four directions associated with it. 
//		//Each direction is associated with an expected discounted utility. 
//		Double state_utility;
//		HashMap<String,Double> expected_discount_utility = new HashMap<String,Double>();
//		
//		for (String dir : mdp.getActions()) {
//			double EDU = 0.0;
//			for (State neighbor : getNeighboringStates(state, mdp)) {
//				EDU += (utility.get(neighbor) * mdp.transProb(state, dir, neighbor));
//				//EDU += utility.get(neighbor);
//			}
//			expected_discount_utility.put(dir, EDU);
//		}
//		//return the state's utility as defined in AIMA: 17.2.1
//		state_utility = (Collections.max(expected_discount_utility.values()) * mdp.getGamma()) + state.reward();
//		return state_utility;
//	}
    
    /**
     * finds the difference between the U' and U
     * @param backUtility U'
     * @param maxUtility U
     * @return utility delta
     */
    private double getUtilityDelta(HashMap<State, Double> backUtility, HashMap<State, Double> maxUtility)
    {
    	double finalDelta = 0.0;
    	
    	for (HashMap.Entry<State,Double> backupState : backUtility.entrySet())
    	{
			double currentStateValue = Math.abs(maxUtility.get(backupState.getKey()));
			double previousStateValue = Math.abs(backupState.getValue());
			double currentStateDelta = Math.abs(currentStateValue - previousStateValue);
			if (currentStateDelta > finalDelta) {
				finalDelta = currentStateDelta;
			}
    	}
    	
    	return finalDelta;
    }

    /**
     * returns neighbors of current state as well as the direction from current state
     * @param sState current state
     * @param mdp
     * @return neighbors and directions from current state
     */
    private Set<State> getNeighbors(State sState, MarkovDecisionProcess mdp)
    {
    	Set<State> neighbors = new HashSet<State>();
    	State eState;
    	for (int i = 0; i < mdp.getStates().size(); i++){
    		eState = mdp.getStates().get(i);
    		for(String direction : mdp.getActions()){
    			double chance = mdp.transProb(sState, direction, eState);
    			if (chance > 0.0)
    			{
    				if(!neighbors.contains(eState)){
    					neighbors.add(eState);
    				}
    			}
    		}
    	}
    	return neighbors;
    }

    /**
     * converges for optimal policy
     * @param mdp
     * @return converged optimal policy
     */
    private HashMap<State, String> getOptimalPolicy(MarkovDecisionProcess mdp)
    {
    	HashMap<State, String> policy = new HashMap<State, String>();
    	HashMap<State, Double> utility = valueIteration(mdp);
    	Set<State> neighbors = new HashSet<State>();
    	HashMap<String, Double> directionPolicy = new HashMap<String, Double>();
    	
    	for(State currState : mdp.getStates())
    	{
    		neighbors = getNeighbors(currState, mdp);
    		State optimalNeighbor = null;
    		double maxU = Double.NEGATIVE_INFINITY;
    		double maxProb = Double.NEGATIVE_INFINITY;
    		String direction = null;
    		for(State neighborState : neighbors){
    			if (utility.get(neighborState) > maxU)
    			{
    				maxU = utility.get(neighborState);
    				optimalNeighbor = neighborState;
    			}
    		}
    		for(State neighborState :neighbors)
    		{
    			for(String action : mdp.getActions())
    			{
    				directionPolicy.put(action, mdp.transProb(currState, action, optimalNeighbor));
    			}
    		}
    		
    		String bestPolicy = null;
    		for (HashMap.Entry<String, Double> pol : directionPolicy.entrySet())
    		{
    			if (pol.getValue() > maxProb){
    				maxProb = pol.getValue();
    				bestPolicy = pol.getKey();
    			}
    		}
    		policy.put(currState, bestPolicy);
    	}
    	
    	return policy;
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
    	
    	//value utility
    	HashMap<State, String> optimalMove = getOptimalPolicy(mdp);
    	return optimalMove.get(mdp.getCurrent());
    }
}
