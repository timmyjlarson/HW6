import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;


/**
 * CISC 380 Algorithms Assignment 5 & 6
 *
 * Represents a graph of nodes and edges in adjacency list format.
 *
 * @author Tim Larson and Jonathan Benusa Due Date: 05/08/22
 */

public class DirectedGraph {

    private static final boolean DEBUG = false;
    private ArrayList<DirectedGraphNode> nodes;


    /**
     * Constructs a directed graph with the given adjacency matrix. The adjacency
     * matrix is a 2d array of booleans representing the presence of edges in the
     * graph.
     *
     * An edge from vertex i to vertex j exists if adacencyMatrix[i][j] is true.
     * @param adjacencyMatrix a 2d boolean array representing an adjacency matrix.
     */
    public DirectedGraph(boolean[][] adjacencyMatrix) {

        nodes = new ArrayList<DirectedGraphNode>();

        // populate the graph with nodes.
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(new DirectedGraphNode(i));
        }

        // connect the nodes based on the adjacency matrix
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j]) {
                    this.connect(i, j);
                }//if
            }
        }

    }// constructor

    /**
     * Constructs a directed graph with the given adjacency list. The graph will
     * have a number vertices equal to the length of the adjacency list. The
     * adajcency list is a 2d array of integers where the array at index i
     * represents the ids of children of vertex i.
     *
     * Values that are negative or greater than length of adjacencyList are ignored.
     *
     * @param adjacencyList a 2d integer array representing an adjacency list of the vertices.
     */


    public DirectedGraph(int[][] adjacencyList) {  //have an uphill adjacency list and a downhill adjacency list
        nodes = new ArrayList<DirectedGraphNode>();

        // populates the graph with nodes.
        for (int i = 0; i < adjacencyList.length; i++) {
            nodes.add(new DirectedGraphNode(i));
        }

        // connect the nodes based on the adjacency list.
        for (int i = 0; i < adjacencyList.length; i++) {
            for (int j = 0; j < adjacencyList[i].length; j++) {

                // if the value in the array is a valid node id, connect them.
                if (-1 < adjacencyList[i][j] && adjacencyList[i][j] < adjacencyList.length) {
                    this.connect(i, adjacencyList[i][j]);
                }
            }

        }

    }// constructor

    /**
     * Retrieves the size of the minimum vertex cover of the directed graph. If the
     * graph does not contain a cycle, it sums the minimum vertex cover of each
     * component.
     *
     * @return the minimum vertex cover of the directed graph, otherwise -1.
     */
    public int getMinVertexCover() {

        int result = -1;


        if (this.isAcyclic() && this.hasValidDegrees()) {
            result++;
            for (DirectedGraphNode root : this.findRoots()) {

                result += this.vCover(root);

            }

        } else if (DEBUG) {
            System.out.println("Invalid Graph detected!");
        }

        return result;

    }// getMinVertexCover

    /**
     * Finds the smallest vertex cover for the given directed tree. A vertex cover
     * is a subset of the vertices in the graph that includes one endpoint of every
     * edge in the graph.
     *
     * Runs in linear time.
     *
     * @return the minimum number of nodes to completely cover the graph.
     *
     */
    private int vCover(DirectedGraphNode root) {

        //YOUR CODE HERE (Assignment 5 only)
        return -1;

    }// vCover

    /**
     * Prints the vertices (the data field for each DirectedGraphNode) included in
     * the smallest vertex cover (i.e. the vertices the correspond to the number
     * returned by getMinVertexCover(). Note that this method should first call the getMinVertexCover
     * method to ensure that the needed coverSize fields are updated.
     *
     * Runs in linear time.
     *
     */
    public void printCover() {
        //YOUR CODE HERE (Assignment 5 only)
    }// printCover

    /**
     * Determines if a cycle exists in the directed graph.
     *
     * @return true, if there are no cycles, false otherwise.
     */
    public boolean isAcyclic() {
        DFSList dfsList = new DFSList();
        //create queue or stack or something?
        dfsList = dfs(dfsList);
        //something like this:
        //return cycleFinder(queue)
        return true;
    }// isAcyclic

    //should be private cycle finder method here
    //should look at each edge on the stack and compare post numbers
    //if back edge behavior, break loop and return false
    //if makes it to the end return true

    private DFSList dfs(DFSList dfsList){
        DirectedGraphNode startNode = findNode(0);
        DirectedGraphNode endNode = findNode(nodes.size());
        for(DirectedGraphNode node : startNode.outgoingNodes){
            BigNode currentNode = new BigNode(false, -1, -1, node);
            dfsList.addNode(currentNode);
        }
        //this is supposed to go accross all the BigNode values stored in the dfsList?
        for(BigNode node : dfsList){
            if(node.isVisited){
                //add this to the stack for later
            } else{
                explore(dfsList, node);
            }
        }

        return dfsList.getDFSList();
    }

    /**
     * This is just the explore method, passed the DFSList so that it can access it
     */
    private DFSList explore(DFSList dfsList, BigNode node){
        node.setIsVisited(true);
        node.setPre(dfsList.getClock());
        dfsList.updateClock();
        //this we want to go across the nodes stemming from the passed node
        for(/*throw some shit in here to iterate across nodes*/){
            if(node.isVisited){
                //add to queue
            } else{
                explore(dfsList, node);
            }
        }
        node.setPost(dfsList.getClock());
        dfsList.updateClock();
    }

    /**
     * This class contains the type BigNode, which is like the normal DirectedGraphNode,
     * just with more data in order to be useful in finding cycles
     */
    private static class BigNode {
        private boolean isVisited;
        private int pre;
        private int post;
        private DirectedGraphNode node;

        /**
         * Constructor for BigNode, everything else is getters and setters so it should
         * be pretty clear
         */
        public BigNode(boolean isVisited, int pre, int post, DirectedGraphNode node){
            this.isVisited = isVisited;
            this.pre = pre;
            this.post = post;
            this.node = node;
        }

        public boolean getIsVisited(){
            return this.isVisited;
        }

        public void setIsVisited(boolean isVisited){
            this.isVisited = isVisited;
        }

        public DirectedGraphNode getNode(){
            return this.node;
        }

        public void setPre(int pre){
            this.pre = pre;
        }

        public void setPost(int post){
            this.post = post;
        }

        public int getPost(){
            return post;
        }
    }

    /**
     * This is supposed to be the list of nodes in the order they are visited,
     * only real use is for DFS
     */
    private class DFSList<BigNode>{     //this shit is wrong, obviously. but why?
        private DFSList<BigNode> dfsList = new ArrayList<>();
        private int clock = 0;

        public void addNode(BigNode node){
            dfsList.add(node);
        }

        public DFSList getDFSList(){
            return this.dfsList;
        }

        public BigNode getNode(int index){
            return dfsList.get(index);
        }

        //these are in here because it is the only object that should be everywhere
        public void updateClock(){
            this.clock = clock++;
        }

        public int getClock(){
            return this.clock;
        }
    }


    /**
     * Retrieves the number of nodes in the Graph.
     *
     * @return the number of nodes in the graph.
     */
    public int getGraphSize() {
        return this.nodes.size();
    }// getGraphSize

    /**
     * Returns a string representation of all the nodes in the graph. The string
     * displays the nodes data, and a list of all of its outgoing Nodes.
     *
     * @return a string representation of the graph.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // for every node
        for (int i = 0; i < this.nodes.size(); i++) {
            // append the string representation to the result.
            DirectedGraphNode current = this.nodes.get(i);
            sb.append(String.format("%-8s vCover: %3d Out: %3d In: %3d\n", current.toString(),current.getCoverSize(),current.getOutDegree(),current.getInDegree()));
        }
        return sb.toString();
    }// toString


    /**
     * adds the node other as a neighbor to root.
     *
     * @param root  the data of the node to receive a neighbor
     * @param other the data of the node to be added
     */

    private void connect(int root, int other) {

        if (0 > root || root >= this.getGraphSize()) {
            throw new ArrayIndexOutOfBoundsException("Cannot connect nonexistent root with value: " + root
                    + ". Valid Nodes are between 0 and " + (this.nodes.size() - 1) + ".");
        }

        if (0 > other || other >= this.getGraphSize()) {
            throw new ArrayIndexOutOfBoundsException("Cannot connect nonexistent root with value: " + other
                    + ". Valid Nodes are between 0 and " + (this.nodes.size() - 1) + ".");

        }

        DirectedGraphNode rootNode = findNode(root);
        DirectedGraphNode otherNode = findNode(other);

        // add the other node to the root
        rootNode.getOutgoingNodes().add(otherNode);
        otherNode.incrementInDegree();

    }// connect

    /**
     * Finds a node in the graph, if it exists.
     *
     * @throws ArrayIndexOutOfBoundsException if the node does not exist.
     * @return a DirectedGraphNode with the given data.
     *
     */

    private DirectedGraphNode findNode(int data) {
        if(0 <= data && data < this.nodes.size()){
            return nodes.get(data);
        }else{
            return null;
        }


    }// findNode

    /**
     * returns an array of integers, representing the vertices that are roots in the
     * Directed Graph
     *
     * @return an array of integers that are roots, or an empty list if there is
     *         none.
     */

    private List<DirectedGraphNode> findRoots() {

        ArrayList<DirectedGraphNode> result = new ArrayList<DirectedGraphNode>();

        // a node is a root if it has an indegree of 0
        // add all nodes with an indegree of 0 to the result array.
        for(DirectedGraphNode node: this.nodes){
            if(node.getInDegree() == 0){
                result.add(node);
            }//if
        }//for

        return result;
    }// findRoots

    /**
     * Checks to see if the in-degrees are valid for the vertex cover algorithm.
     *
     * @return true, if all nodes have an in-degree of 0 or 1. False otherwise.
     *
     */

    private boolean hasValidDegrees() {
        boolean result = true;

        for (DirectedGraphNode node : this.nodes) {
            result = result && (node.getInDegree() == 0 || node.getInDegree() == 1);

        } // for

        return result;

    }// hasValidDegrees



    /**
     * Representation of a vertex of the graph, uniquely identified by the data.
     */
    private static class DirectedGraphNode {

        private int data;
        private int inDegree;

        private Integer coverSize;

        private LinkedList<DirectedGraphNode> outgoingNodes;
        

        public DirectedGraphNode(int data) {

            this.data = data;
            this.coverSize = null;
            this.outgoingNodes = new LinkedList<DirectedGraphNode>();
            this.inDegree = 0;

        }// constructor

        /**
         * increments the in degree.
         *
         */
        public void incrementInDegree() {
            this.inDegree++;
        }


        /**
         * returns this node's in degree.
         * This is the amount of nodes that this node has as a parent.
         * @return the in degree of this node.
         */

        public int getInDegree() {
            return this.inDegree;
        }//getInDegree

        /**
         * returns this node's out degree.
         * This is the amount of children nodes this node has.
         * @return the out degree of this node.
         */

        public int getOutDegree() {
            return this.outgoingNodes.size();
        }

        /**
         * getter method for the data of the node. This should uniquely identify the
         * node.
         *
         * @return the data within this node.
         */
        public int getData() {
            return this.data;
        }// getData

        /**
         * getter method for the cover size of the node.
         *
         * @return the size of the vertex cover stored in this Node, or null if the
         *         cover size hasn't been set yet.
         *
         */
        public Integer getCoverSize() {
            return this.coverSize;
        }// isCovered

        /**
         * setter method for the cover size.
         *
         * @param size the new size for this node.
         */
        public void setCoverSize(Integer size) {
            this.coverSize = size;
        }// setCoverSize

        /**
         * retrieves a reference to a list of this node's outgoingNodes.
         *
         * @return a LinkedList of nodes that are connected to this node.
         *
         */
        public List<DirectedGraphNode> getOutgoingNodes() {
            return this.outgoingNodes;
        }// getNeighbors

        /**
         * returns a string representation of the node. Displays the current data of the
         * node, and a list of the data of all of its outgoingNodes.
         *
         * @return a string representation of the node.
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(this.getData() + ":[");

            for (int i = 0; i < this.outgoingNodes.size(); i++) {
                if (i == this.outgoingNodes.size() - 1) {
                    sb.append(outgoingNodes.get(i).getData());
                } else {
                    sb.append(outgoingNodes.get(i).getData() + ", ");
                } // else

            } // for
            sb.append("]");
            return sb.toString();

        }// toString

    }// class DirectedGraphNode

}// class
