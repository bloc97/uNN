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
public interface GraphNode extends Iterable {
    public int size();
    
    
    public GraphLink getLink(int i);
    public GraphLink[] getAllLinks();
    public GraphLink[] getLinks(GraphNode node);
    
    public boolean isLinked(GraphNode node);
    
    public void addDirectedLink(GraphNode node);
    public void addBiDirectedLink(GraphNode node);
    public void addUndirectedLink(GraphNode node);
    
    public void addLink(GraphLink link);
    public void removeLink(GraphLink link);
    
    public void removeLinks(GraphNode node); //Removes all the edges pointing from node 1 to node 2, if it is a undirected edge, removes also the node 2 to node 1 pointer
    public void cutAllLinks(GraphNode node); //Cuts all the links between the two nodes, node 1 and node 2 will be completely separated, with all edges pointing in either direction removed.
}
