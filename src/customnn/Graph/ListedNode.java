/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn.Graph;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class ListedNode implements GraphNode {
    
    protected LinkedList<GraphLink> links;
    
    public ListedNode() {
        this.links = new LinkedList<>();
    }
    public ListedNode(GraphLink... links) {
        this.links = new LinkedList<>(Arrays.asList(links));
    }
    public ListedNode(GraphNode... nodes) {
        this();
        for (GraphNode node : nodes) {
            addDirectedLink(node);
        }
    }

    @Override
    public int size() {
        return links.size();
    }

    @Override
    public boolean isLinked(GraphNode node) {
        for (GraphLink link : links) {
            if (link.checkOther(this, node)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public GraphLink getLink(int i) {
        return links.get(i);
    }

    
    @Override
    public GraphLink[] getAllLinks() {
        return links.toArray(new GraphLink[0]);
    }
    
    @Override
    public GraphLink[] getLinks(GraphNode node) {
        LinkedList<GraphLink> list = new LinkedList<GraphLink>();
        for (GraphLink link : links) {
            if (link.checkOther(this, node)) {
                list.add(link);
            }
        }
        return list.toArray(new GraphLink[0]);
    }

    @Override
    public void addLink(GraphLink link) {
        if (link.checkSelf(this)) {
            links.add(link);
        } else {
            throw new IllegalArgumentException("Adding wrong link to node!");
        }
    }

    @Override
    public void removeLink(GraphLink link) {
        if (link.checkSelf(this)) {
            links.remove(link);
        } else {
            throw new IllegalArgumentException("Removing wrong link from node!");
        }
    }

    
    @Override
    public void addDirectedLink(GraphNode node) {
        GraphLink link = new DirectedLink(this, node);
        this.addLink(link);
    }
    
    @Override
    public void addBiDirectedLink(GraphNode node) {
        node.addDirectedLink(this);
        this.addDirectedLink(node);
    }
    
    @Override
    public void addUndirectedLink(GraphNode node) {
        GraphLink link = new UndirectedLink(this, node);
        this.addLink(link);
        node.addLink(link);
    }


    @Override
    public void removeLinks(GraphNode node) {
        for (GraphLink link : links) {
            if (link.checkOther(this, node)) {
                
                if (link.isDirected()) {
                    
                    this.removeLink(link);
                    
                } else {
                    
                    this.removeLink(link);
                    node.removeLink(link);
                    
                }
            }
        }
    }

    @Override
    public void cutAllLinks(GraphNode node) {
        node.removeLinks(this);
        this.removeLinks(node);
    }
    
    @Override
    public Iterator<GraphLink> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
