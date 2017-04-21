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
public class UndirectedLink implements GraphLink {
    protected GraphNode n1, n2;
    public UndirectedLink(GraphNode node1, GraphNode node2) {
        n1 = node1;
        n2 = node2;
    }
    @Override
    public GraphNode getOther(GraphNode thisNode) {
        if (thisNode == n1) {
            return n2;
        } else if (thisNode == n2) {
            return n1;
        } else {
            throw new IllegalStateException("Link added to wrong node!");
        }
    }

    @Override
    public boolean isDirected() {
        return false;
    }
    
    @Override
    public boolean checkSelf(GraphNode thisNode) {
        return thisNode == n1 || thisNode == n2;
    }

    @Override
    public boolean checkOther(GraphNode thisNode, GraphNode otherNode) {
        if (thisNode == n1) {
            return otherNode == n2;
        } else if (thisNode == n2) {
            return otherNode == n1;
        } else {
            return false;
        }
    }
    
}
