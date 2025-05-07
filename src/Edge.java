/*
Student Name: Inuka Nimsara Wickramasinghe
Student ID: w2052208
*/

public class Edge {
    int from;
    int to;
    int capacity;
    int flow;
    Edge reverse;

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int getResidualCapacity() {
        return capacity - flow;
    }

    public void addFlow(int flowAmount) {
        this.flow += flowAmount;
        this.reverse.flow -= flowAmount;
    }
}
