package com.my.algorithms.week5.third;

import java.util.Scanner;

public class CycleSearchTask1 {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final int vertexes = scanner.nextInt();
        final int edges = scanner.nextInt();
        final int[][] graph = new int[vertexes][];
        scanner.nextLine();
        for (int index = 0; index < edges; index++) {
            addDirected(graph, scanner.nextInt(), scanner.nextInt());
        }
        dsg(graph);

        /*
        final int[][] graph = new int[4][];
        addDirected(graph, 1, 2);
        addDirected(graph, 4, 1);
        addDirected(graph, 2, 3);
        addDirected(graph, 3, 1);
        System.out.println("Graph: " + Graphs.toString(graph));
        System.out.println("Working times: " + Arrays.toString(dsg(graph)) + "\n");

        final int[][] graph2 = new int[7][];
        addDirected(graph2, 1, 2);
        addDirected(graph2, 1, 3);
        addDirected(graph2, 2, 3);
        addDirected(graph2, 2, 4);
        addDirected(graph2, 3, 4);
        addDirected(graph2, 5, 1);
        addDirected(graph2, 5, 6);
        addDirected(graph2, 6, 7);
        addDirected(graph2, 5, 7);
        System.out.println("Graph: " + Graphs.toString(graph2));
        System.out.println("Working times: " + Arrays.toString(dsg(graph2)));
        */
    }

    private static void addDirected(final int[][] graph, final int firstVertex, final int secondVertex) {
        addEdge(graph, firstVertex - 1, secondVertex - 1);
    }

    private static void addEdge(final int[][] graph, final int firstVertex, final int secondVertex) {
        if (graph[firstVertex] != null) {
            int length = graph[firstVertex].length;
            for (int edgeIndex = 0; edgeIndex < length; edgeIndex++) {
                if (graph[firstVertex][edgeIndex] == secondVertex) {
                    return;
                }
            }
            final int[] edges = new int[length + 1];
            System.arraycopy(graph[firstVertex], 0, edges, 0, length);
            edges[length] = secondVertex;
            graph[firstVertex] = edges;
        } else {
            graph[firstVertex] = new int[]{secondVertex};
        }
    }

    private static WorkingTime[] dsg(final int[][] graph) {
        final WorkingTime[] visited = new WorkingTime[graph.length];
        final Time cycle = new Time(0);
        dsg(graph, visited, cycle);
        // System.out.println("Number of cycles: " + cycle.value);
        System.out.println(cycle.value > 0 ? 1 : 0);
        return visited;
    }

    private static void dsg(final int[][] graph, final WorkingTime[] times, Time cycle) {
        final Time time = new Time(0);
        for (int vertexIndex = 0; vertexIndex < graph.length; vertexIndex++) {
            if (times[vertexIndex] == null) {
                explore(graph, vertexIndex, times, time, cycle);
            }
        }
    }

    private static void explore(final int[][] graph, final int vertex, final WorkingTime[] times, final Time time, final Time cycle) {
        times[vertex] = new WorkingTime(time.incrementAndGet());
        if (graph[vertex] != null) {
            for (int edgeIndex = 0; edgeIndex < graph[vertex].length; edgeIndex++) {
                final int edge = graph[vertex][edgeIndex];
                if (times[edge] == null) {
                    explore(graph, edge, times, time, cycle);
                } else if (times[edge].end == 0) {
                    // System.out.println("fromVertex: " + vertex + ", toVertex: " + edge);
                    cycle.incrementAndGet();
                }
            }
        }
        times[vertex].setEnd(time.incrementAndGet());
    }

    private static class Time {

        private int value;

        public Time(int value) {
            this.value = value;
        }

        public int incrementAndGet() {
            return ++value;
        }
    }

    private static class WorkingTime {

        private int start;
        private int end;

        public WorkingTime(int start) {
            this.start = start;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return "{" + start + ", " + end + '}';
        }
    }
}
