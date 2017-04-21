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
public interface GraphLink {
    public boolean isDirected();
    public boolean checkSelf(GraphNode thisNode);
    public boolean checkOther(GraphNode thisNode, GraphNode otherNode);
    public GraphNode getOther(GraphNode thisNode);
}
