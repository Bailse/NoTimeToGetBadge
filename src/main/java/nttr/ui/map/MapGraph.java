package nttr.ui.map;

import java.util.*;

/**
 * Small graph for map paths (nodes + edges).
 * Clicking a destination will route along edges (A* / Dijkstra).
 */
public class MapGraph {

    public static final class Node {
        public final String id;
        public final double x;
        public final double y;

        public Node(String id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    public static final class Edge {
        public final String a;
        public final String b;

        public Edge(String a, String b) {
            this.a = a;
            this.b = b;
        }
    }

    private final Map<String, Node> nodes = new LinkedHashMap<>();
    private final Map<String, List<String>> adj = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    public void addNode(String id, double x, double y) {
        nodes.put(id, new Node(id, x, y));
        adj.putIfAbsent(id, new ArrayList<>());
    }

    public void addEdge(String a, String b) {
        if (!nodes.containsKey(a) || !nodes.containsKey(b)) return;
        adj.get(a).add(b);
        adj.get(b).add(a);
        edges.add(new Edge(a, b));
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Node> route(String startId, String goalId) {
        if (startId == null || goalId == null) return List.of();
        if (!nodes.containsKey(startId) || !nodes.containsKey(goalId)) return List.of();
        if (startId.equals(goalId)) return List.of(nodes.get(startId));

        // Dijkstra (small graph)
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (String id : nodes.keySet()) dist.put(id, Double.POSITIVE_INFINITY);
        dist.put(startId, 0.0);
        pq.add(startId);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (u.equals(goalId)) break;

            for (String v : adj.getOrDefault(u, List.of())) {
                double alt = dist.get(u) + distance(nodes.get(u), nodes.get(v));
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        ArrayList<Node> path = new ArrayList<>();
        String cur = goalId;
        while (cur != null) {
            path.add(nodes.get(cur));
            cur = prev.get(cur);
        }
        Collections.reverse(path);
        return path;
    }

    public static double distance(Node a, Node b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
