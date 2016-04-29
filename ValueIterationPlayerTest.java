import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

/**
 * A test class for the ValueIterationPlayer.
 * 
 * @author Mitch Parry
 * @version 2014-03-28
 * 
 */
public class ValueIterationPlayerTest
{
    private static final String SIMPLE_G10_R3 =
        "11\na1 -3\na2 -3\na3 -3\na4 100\nb1 -3\nb3 -3\nb4 -100\nc1 -3\n"
            + "c2 -3\nc3 -3\nc4 -3\na4 b4\n4\nN 0.8 0.1 0.0 0.1\n"
            + "E 0.1 0.8 0.1 0.0\nS 0.0 0.1 0.8 0.1\nW 0.1 0.0 0.1 0.8\n"
            + "36\na1 N a1\na1 E a2\na1 S b1\na1 W a1\na2 N a2\na2 E a3\n"
            + "a2 S a2\na2 W a1\na3 N a3\na3 E a4\na3 S b3\na3 W a2\nb1 N a1\n"
            + "b1 E b1\nb1 S c1\nb1 W b1\nb3 N a3\nb3 E b4\nb3 S c3\nb3 W b3\n"
            + "c1 N b1\nc1 E c2\nc1 S c1\nc1 W c1\nc2 N c2\nc2 E c3\nc2 S c2\n"
            + "c2 W c1\nc3 N b3\nc3 E c4\nc3 S c3\nc3 W c2\nc4 N b4\nc4 E c4\n"
            + "c4 S c4\nc4 W c3\n1.0\nc1";
    private static final String SIMPLE_G10_R1 =
        "11\na1 -1\na2 -1\na3 -1\na4 100\nb1 -1\nb3 -1\nb4 -100\nc1 -1\n"
            + "c2 -1\nc3 -1\nc4 -1\na4 b4\n4\nN 0.8 0.1 0.0 0.1\n"
            + "E 0.1 0.8 0.1 0.0\nS 0.0 0.1 0.8 0.1\nW 0.1 0.0 0.1 0.8\n"
            + "36\na1 N a1\na1 E a2\na1 S b1\na1 W a1\na2 N a2\na2 E a3\n"
            + "a2 S a2\na2 W a1\na3 N a3\na3 E a4\na3 S b3\na3 W a2\nb1 N a1\n"
            + "b1 E b1\nb1 S c1\nb1 W b1\nb3 N a3\nb3 E b4\nb3 S c3\nb3 W b3\n"
            + "c1 N b1\nc1 E c2\nc1 S c1\nc1 W c1\nc2 N c2\nc2 E c3\nc2 S c2\n"
            + "c2 W c1\nc3 N b3\nc3 E c4\nc3 S c3\nc3 W c2\nc4 N b4\nc4 E c4\n"
            + "c4 S c4\nc4 W c3\n1.0\nc1";
    private static final String SIMPLE_G09_R0 =
        "11\na1 0\na2 0\na3 0\na4 100\nb1 0\nb3 0\nb4 -100\nc1 0\nc2 0\nc3 0\n"
            + "c4 0\na4 b4\n4\nN 0.8 0.1 0.0 0.1\nE 0.1 0.8 0.1 0.0\n"
            + "S 0.0 0.1 0.8 0.1\nW 0.1 0.0 0.1 0.8\n36\na1 N a1\na1 E a2\n"
            + "a1 S b1\na1 W a1\na2 N a2\na2 E a3\na2 S a2\na2 W a1\na3 N a3\n"
            + "a3 E a4\na3 S b3\na3 W a2\nb1 N a1\nb1 E b1\nb1 S c1\nb1 W b1\n"
            + "b3 N a3\nb3 E b4\nb3 S c3\nb3 W b3\nc1 N b1\nc1 E c2\nc1 S c1\n"
            + "c1 W c1\nc2 N c2\nc2 E c3\nc2 S c2\nc2 W c1\nc3 N b3\nc3 E c4\n"
            + "c3 S c3\nc3 W c2\nc4 N b4\nc4 E c4\nc4 S c4\nc4 W c3\n0.9\nc1";
    private static final String TUNNEL_A2_G10_R1 =
        "11\na1 -1\na2 -1\na3 -1\na4 100\nb1 -1\nb3 -1\nb4 -100\nc1 -1\n"
            + "c2 -1\nc3 -1\nc4 -1\na4 b4\n4\nN 0.8 0.1 0.0 0.1\n"
            + "E 0.1 0.8 0.1 0.0\nS 0.0 0.1 0.8 0.1\nW 0.1 0.0 0.1 0.8\n36\n"
            + "a1 N a1\na1 E a2\na1 S b1\na1 W a1\na2 N a2\na2 E a3\na2 S a2\n"
            + "a2 W a1\na3 N a3\na3 E a4\na3 S b3\na3 W a2\nb1 N a1\nb1 E b1\n"
            + "b1 S c1\nb1 W b1\nb3 N a3\nb3 E b4\nb3 S c3\nb3 W b3\nc1 N b1\n"
            + "c1 E c2\nc1 S c1\nc1 W c1\nc2 N a2\nc2 E c3\nc2 S c2\nc2 W c1\n"
            + "c3 N b3\nc3 E c4\nc3 S c3\nc3 W c2\nc4 N b4\nc4 E c4\nc4 S c4\n"
            + "c4 W c3\n1.0\nc1";
    private static final String TUNNEL_A4_G10_R1 =
        "11\na1 -1\na2 -1\na3 -1\na4 100\nb1 -1\nb3 -1\nb4 -100\nc1 -1\n"
            + "c2 -1\nc3 -1\nc4 -1\na4 b4\n4\nN 0.8 0.1 0.0 0.1\n"
            + "E 0.1 0.8 0.1 0.0\nS 0.0 0.1 0.8 0.1\nW 0.1 0.0 0.1 0.8\n36\n"
            + "a1 N a1\na1 E a2\na1 S b1\na1 W a1\na2 N a2\na2 E a3\na2 S a2\n"
            + "a2 W a1\na3 N a3\na3 E a4\na3 S b3\na3 W a2\nb1 N a1\nb1 E b1\n"
            + "b1 S c1\nb1 W b1\nb3 N a3\nb3 E b4\nb3 S c3\nb3 W b3\nc1 N b1\n"
            + "c1 E c2\nc1 S c1\nc1 W c1\nc2 N a4\nc2 E c3\nc2 S c2\nc2 W c1\n"
            + "c3 N b3\nc3 E c4\nc3 S c3\nc3 W c2\nc4 N b4\nc4 E c4\nc4 S c4\n"
            + "c4 W c3\n1.0\nc1";
    private static final String WORLD0 =
        "98\na1 -3\na2 -100\na3 -3\na4 -3\na5 -100\na6 -3\na7 -3\na8 -3\n"
            + "a9 -3\na10 100\nb1 -3\nb2 -3\nb3 -3\nb4 -3\nb5 -3\nb6 -3\n"
            + "b7 -3\nb8 -3\nb9 -3\nb10 -3\nc1 -3\nc2 -3\nc3 -100\nc4 -100\n"
            + "c5 -3\nc6 -100\nc7 -3\nc8 -3\nc9 -3\nc10 -3\nd1 -3\nd2 -100\n"
            + "d3 -3\nd4 -3\nd5 -3\nd6 -3\nd7 -3\nd8 -3\nd9 -3\nd10 -3\ne1 -3\n"
            + "e2 -3\ne3 -3\ne4 -3\ne5 -3\ne6 -3\ne7 -3\ne8 -3\ne9 -100\n"
            + "e10 -3\nf1 -3\nf2 -3\nf3 -100\nf4 -3\nf5 -3\nf6 -3\nf7 -3\n"
            + "f8 -3\nf9 -3\ng1 -3\ng2 -3\ng3 -3\ng4 -3\ng5 -3\ng6 -3\ng7 -3\n"
            + "g8 -3\ng9 -3\ng10 -3\nh1 -3\nh2 -3\nh3 -3\nh4 -100\nh5 -3\n"
            + "h6 -3\nh7 -3\nh8 -3\nh9 -3\nh10 -3\ni1 -3\ni2 -3\ni3 -3\n"
            + "i5 -3\ni6 -3\ni7 -3\ni8 -3\ni9 -100\ni10 -3\nj1 -3\nj2 -3\n"
            + "j3 -3\nj4 -3\nj5 -3\nj6 -3\nj7 -3\nj8 -3\nj9 -3\nj10 -3\n"
            + "a5 c3 i9 f3 c6 e9 a2 d2 c4 h4 a10 \n4\n"
            + "N 0.931074 0.029583 0.009759 0.029583 \n"
            + "E 0.029583 0.931074 0.029583 0.009759 \n"
            + "S 0.009759 0.029583 0.931074 0.029583 \n"
            + "W 0.029583 0.009759 0.029583 0.931074 \n"
            + "348\na1 N a1\na1 E a2\na1 S b1\na1 W a1\na3 N a3\na3 E a4\n"
            + "a3 S b3\na3 W a2\na4 N a4\na4 E a5\na4 S b4\na4 W a3\na6 N a6\n"
            + "a6 E a7\na6 S b6\na6 W a5\na7 N a7\na7 E a8\na7 S b7\na7 W a6\n"
            + "a8 N a8\na8 E a9\na8 S b8\na8 W a7\na9 N a9\na9 E a10\n"
            + "a9 S b9\na9 W a8\nb1 N a1\nb1 E b2\nb1 S c1\nb1 W b1\nb2 N a2\n"
            + "b2 E b3\nb2 S c2\nb2 W b1\nb3 N a3\nb3 E b4\nb3 S c3\nb3 W b2\n"
            + "b4 N a4\nb4 E b5\nb4 S c4\nb4 W b3\nb5 N a5\nb5 E b6\nb5 S c5\n"
            + "b5 W b4\nb6 N a6\nb6 E b7\nb6 S c6\nb6 W b5\nb7 N a7\nb7 E b8\n"
            + "b7 S c7\nb7 W b6\nb8 N a8\nb8 E b9\nb8 S c8\nb8 W b7\nb9 N a9\n"
            + "b9 E b10\nb9 S c9\nb9 W b8\nb10 N a10\nb10 E b10\nb10 S c10\n"
            + "b10 W b9\nc1 N b1\nc1 E c2\nc1 S d1\nc1 W c1\nc2 N b2\n"
            + "c2 E c3\nc2 S d2\nc2 W c1\nc5 N b5\nc5 E c6\nc5 S d5\n"
            + "c5 W c4\nc7 N b7\nc7 E c8\nc7 S d7\nc7 W c6\nc8 N b8\n"
            + "c8 E c9\nc8 S d8\nc8 W c7\nc9 N b9\nc9 E c10\nc9 S d9\n"
            + "c9 W c8\nc10 N b10\nc10 E c10\nc10 S d10\nc10 W c9\nd1 N c1\n"
            + "d1 E d2\nd1 S e1\nd1 W d1\nd3 N c3\nd3 E d4\nd3 S e3\nd3 W d2\n"
            + "d4 N c4\nd4 E d5\nd4 S e4\nd4 W d3\nd5 N c5\nd5 E d6\nd5 S e5\n"
            + "d5 W d4\nd6 N c6\nd6 E d7\nd6 S e6\nd6 W d5\nd7 N c7\nd7 E d8\n"
            + "d7 S e7\nd7 W d6\nd8 N c8\nd8 E d9\nd8 S e8\nd8 W d7\nd9 N c9\n"
            + "d9 E d10\nd9 S e9\nd9 W d8\nd10 N c10\nd10 E d10\nd10 S e10\n"
            + "d10 W d9\ne1 N d1\ne1 E e2\ne1 S f1\ne1 W e1\ne2 N d2\n"
            + "e2 E e3\ne2 S f2\ne2 W e1\ne3 N d3\ne3 E e4\ne3 S f3\n"
            + "e3 W e2\ne4 N d4\ne4 E e5\ne4 S f4\ne4 W e3\ne5 N d5\n"
            + "e5 E e6\ne5 S f5\ne5 W e4\ne6 N d6\ne6 E e7\ne6 S f6\n"
            + "e6 W e5\ne7 N d7\ne7 E e8\ne7 S f7\ne7 W e6\ne8 N d8\n"
            + "e8 E e9\ne8 S f8\ne8 W e7\ne10 N d10\ne10 E e10\ne10 S j4\n"
            + "e10 W e9\nf1 N e1\nf1 E f2\nf1 S g1\nf1 W f1\nf2 N e2\n"
            + "f2 E f3\nf2 S g2\nf2 W f1\nf4 N e4\nf4 E f5\nf4 S g4\n"
            + "f4 W f3\nf5 N e5\nf5 E f6\nf5 S g5\nf5 W f4\nf6 N e6\n"
            + "f6 E f7\nf6 S g6\nf6 W f5\nf7 N e7\nf7 E f8\nf7 S g7\n"
            + "f7 W f6\nf8 N e8\nf8 E f9\nf8 S g8\nf8 W f7\nf9 N e9\n"
            + "f9 E i5\nf9 S g9\nf9 W f8\ng1 N f1\ng1 E g2\ng1 S h1\n"
            + "g1 W g1\ng2 N f2\ng2 E g3\ng2 S h2\ng2 W g1\ng3 N f3\n"
            + "g3 E g4\ng3 S h3\ng3 W g2\ng4 N f4\ng4 E g5\ng4 S h4\n"
            + "g4 W g3\ng5 N f5\ng5 E g6\ng5 S h5\ng5 W g4\ng6 N f6\n"
            + "g6 E g7\ng6 S h6\ng6 W g5\ng7 N f7\ng7 E g8\ng7 S h7\n"
            + "g7 W g6\ng8 N f8\ng8 E g9\ng8 S h8\ng8 W g7\ng9 N f9\n"
            + "g9 E g10\ng9 S h9\ng9 W g8\ng10 N h4\ng10 E g10\ng10 S h10\n"
            + "g10 W g9\nh1 N g1\nh1 E h2\nh1 S i1\nh1 W h1\nh2 N g2\n"
            + "h2 E h3\nh2 S i2\nh2 W h1\nh3 N g3\nh3 E h4\nh3 S i3\n"
            + "h3 W h2\nh5 N g5\nh5 E h6\nh5 S i5\nh5 W h4\nh6 N g6\n"
            + "h6 E h7\nh6 S i6\nh6 W h5\nh7 N g7\nh7 E h8\nh7 S i7\n"
            + "h7 W h6\nh8 N g8\nh8 E h9\nh8 S i8\nh8 W h7\nh9 N g9\n"
            + "h9 E h10\nh9 S i9\nh9 W h8\nh10 N g10\nh10 E h10\n"
            + "h10 S i10\nh10 W h9\ni1 N h1\ni1 E i2\ni1 S j1\ni1 W i1\n"
            + "i2 N h2\ni2 E i3\ni2 S j2\ni2 W i1\ni3 N h3\ni3 E i3\n"
            + "i3 S j3\ni3 W i2\ni5 N h5\ni5 E i6\ni5 S j5\ni5 W f9\n"
            + "i6 N h6\ni6 E i7\ni6 S j6\ni6 W i5\ni7 N h7\ni7 E i8\n"
            + "i7 S j7\ni7 W i6\ni8 N h8\ni8 E i9\ni8 S j8\ni8 W i7\n"
            + "i10 N h10\ni10 E i10\ni10 S j10\ni10 W i9\nj1 N i1\n"
            + "j1 E j2\nj1 S j1\nj1 W j1\nj2 N i2\nj2 E j3\nj2 S j2\n"
            + "j2 W j1\nj3 N i3\nj3 E j4\nj3 S j3\nj3 W j2\nj4 N e10\n"
            + "j4 E j5\nj4 S j4\nj4 W j3\nj5 N i5\nj5 E j6\nj5 S j5\n"
            + "j5 W j4\nj6 N i6\nj6 E j7\nj6 S j6\nj6 W j5\nj7 N i7\n"
            + "j7 E j8\nj7 S j7\nj7 W j6\nj8 N i8\nj8 E j9\nj8 S j8\n"
            + "j8 W j7\nj9 N i9\nj9 E j10\nj9 S j9\nj9 W j8\nj10 N i10\n"
            + "j10 E j10\nj10 S j10\nj10 W j9\n1.0\nj1";

    /**
     * Helper method to check the solution and print the error.
     * 
     * @param correct
     *            the correct policy.
     * @param mdp
     *            the MDP.
     */
    private void checkSolution(String[] correct, MarkovDecisionProcess mdp)
    {
        HashMap<State, String> map = new HashMap<State, String>();
        HashMap<State, String> correctMap = new HashMap<State, String>();
        String[] answer = new String[correct.length];
        ValueIterationPlayer p = new ValueIterationPlayer("VIPlayer");
        List<State> states = mdp.getStates();
        int i = 0;
        for (State s : states)
        {
            if (!s.isTerminal())
            {
                mdp.setCurrent(s);
                String a = p.play((MarkovDecisionProcess) mdp.clone());
                answer[i] = a;
                map.put(s, a);
                correctMap.put(s, correct[i]);
                i++;
            }
        }
        assertArrayEquals(
            "Correct:\n" + GridWorld.toString(states, null, correctMap)
                + "\nYour policy:\n" + GridWorld.toString(states, null, map),
            correct, answer);
    }

    /**
     * Test 1.
     */
    @Test
    public void test1()
    {
        final String[] CORRECT = {
            "E", "E", "E", "N", "N", "N", "W", "W", "W"
        };
        MarkovDecisionProcess mdp =
            new MarkovDecisionProcess(new Scanner(SIMPLE_G10_R3));
        checkSolution(CORRECT, mdp);
    }

    /**
     * Test 2.
     */
    @Test
    public void test2()
    {
        final String[] CORRECT = {
            "E", "E", "E", "N", "W", "N", "W", "W", "S"
        };
        MarkovDecisionProcess mdp =
            new MarkovDecisionProcess(new Scanner(SIMPLE_G10_R1));
        checkSolution(CORRECT, mdp);
    }

    /**
     * Test 3.
     */
    @Test
    public void test3()
    {
        final String[] CORRECT = {
            "E", "E", "E", "N", "N", "N", "W", "N", "W"
        };
        MarkovDecisionProcess mdp =
            new MarkovDecisionProcess(new Scanner(SIMPLE_G09_R0));
        checkSolution(CORRECT, mdp);
    }

    /**
     * Test 4.
     */
    @Test
    public void test4()
    {
        final String[] CORRECT = {
            "E", "E", "E", "N", "W", "E", "N", "W", "S"
        };
        MarkovDecisionProcess mdp =
            new MarkovDecisionProcess(new Scanner(TUNNEL_A2_G10_R1));
        checkSolution(CORRECT, mdp);
    }

    /**
     * Test 5.
     */
    @Test
    public void test5()
    {
        final String[] CORRECT = {
            "E", "E", "E", "S", "W", "E", "N", "W", "S"
        };
        MarkovDecisionProcess mdp =
            new MarkovDecisionProcess(new Scanner(TUNNEL_A4_G10_R1));
        checkSolution(CORRECT, mdp);
    }
}
