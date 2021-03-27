import java.util.*

private fun readLn() = readLine()!! // string line
private fun readInt() = readLn().toInt() // single int
private fun readLong() = readLn().toLong() // single long
private fun readDouble() = readLn().toDouble() // single double
private fun readStrings() = readLn().split(" ") // list of strings
private fun readInts() = readStrings().map { it.toInt() } // list of ints
private fun readLongs() = readStrings().map { it.toLong() } // list of longs
private fun readDoubles() = readStrings().map { it.toDouble() } // list of doubles

var n : Int = 1024

var visited = BooleanArray(n) { false }
var adj = Array(n) { mutableListOf<Pair<Int,Int>>() }



fun toLeaf(root : Int) : Int {
    if(adj[root].size == 0) return 0
    var max = 0
    for (i in adj[root]){
        max = maxOf(max, 1 + toLeaf(i.first))
    }
    return max
}

fun diameter() : Int{
    var dia = 0
    for (i in 0 until n){
        var max1 = 0
        var max2 = 0
        for (j in adj[0]){
            var t = 1 + toLeaf(j.first)
            if (t > max1){
                max2 = max1
                max1 = t
            }
            dia = maxOf(dia, max1 + max2)
        }
    }
    return dia
}

fun dfs(root : Int){
    if (visited[root]) return
    visited[root] = true
    println(root)
    for(i in adj[root]){
        dfs(i.first)
    }
}

fun dikstra(root : Int) : Array<Int> {
    var distance = Array(n) { Int.MAX_VALUE }
    var processed = BooleanArray(n) { false }
    distance[root] = 0
    val compareByDistance: Comparator<Pair<Int,Int>> = compareBy { it.first }
    var Pq = PriorityQueue<Pair<Int,Int>>(compareByDistance)
    Pq.add(Pair(0,root))
    while (Pq.isNotEmpty()){
        var a = Pq.peek().second
        Pq.remove()
        if(processed[a]) continue
        processed[a] = true
        for( u in adj[a]){
            var b = u.first
            var w = u.second
            if(distance[a] + w < distance[b]){
                distance[b] = distance[a] + w
                Pq.add(Pair(-distance[b],b))
            }
        }
    }
    return distance
}

fun main(){
    n = readInt()
    var q = readInt()

    for (i in 0 until q){
        val (a,b,c) = readInts()
        adj[a - 1].add(Pair(b - 1,c))
        //adj[b - 1].add(Pair(a-1,c))
    }
//    val distance = dikstra(0)
//    for (i in distance)
//        println(i)
    println(diameter())

}
