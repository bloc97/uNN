/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.Graph;

/**
 *
 * @author bowen
 */
public class DirectedLink implements GraphLink {
    protected GraphNode tN, oN;
    public DirectedLink(GraphNode thisNode, GraphNode otherNode) {
        tN = thisNode;
        oN = otherNode;
    }
    @Override
    public GraphNode getOther(GraphNode thisNode) {
        if (thisNode == tN) {
            return oN;
        } else {
            throw new IllegalStateException("Link added to wrong node!");
        }
    }

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public boolean checkSelf(GraphNode thisNode) {
        return thisNode == tN;
    }

    @Override
    public boolean checkOther(GraphNode thisNode, GraphNode otherNode) {
        return thisNode == tN && otherNode == oN;
    }
    
}
