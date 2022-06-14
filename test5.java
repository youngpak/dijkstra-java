package test5;
public class test5{
	public static void main(String[] args) {
		Dijkstra d = new Dijkstra(20);
		
		//인접한 두 꼭지점 사이의 가중치 주입
		d.input("a","b",2);
		d.input("a","e",5);
		d.input("b","c",2);
		d.input("b","f",5);
		d.input("c","d",3);
		d.input("c","g",5);
		d.input("d","i",6);
		d.input("e","f",2);
		d.input("e","j",1);
		d.input("f","g",2);
		d.input("g","o",3);
		d.input("g","h",2);
		d.input("h","i",1);
		d.input("i","t",3);
		d.input("i","r",10);
		d.input("t","r",11);
		d.input("j","k",4);
		d.input("k","l",5);
		d.input("l","m",4);
		d.input("o","p",2);
		d.input("o","n",3);
		d.input("n","m",3);
		d.input("m","q",2);
		d.input("q","p",8);
		d.input("p","r",6);
		d.input("r","s",5);
		
		//시작점 a에서부터의 최단거리 및 최단경로 출력
		d.algorithm("i");
	}

}
class Dijkstra {
	private int n; //꼭지점 수를 변수로 선언
	private int[][] weight; //2차원 배열 weight에 각 꼭지점의 가중치를 저장
	private String[] saveRoute;
	private String[] vertex = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t"};
	
	
	public Dijkstra(int n) {
		super();
		this.n = n; //생성자를 통해 꼭지점 수를 주입하고,
		weight = new int[n][n]; //가중치를 저장할 배열 weight의 크기 지정.
		saveRoute = new String[n];
	}
	
	public int stringToInt(String s) {
		//문자열을 int형으로 바꿔준다.
		int x = 0;
		for(int i=0; i<vertex.length; i++) {
			if(vertex[i]==s) x=i;
		}
		return x;
	}
	
	public void input(String v1, String v2, int w) {
		//먼저 문자열로 입력된 꼭지점 v1와 v2를 해당되는 숫자 인덱스 x1과 x2로 바꿔준다.
		int x1 = stringToInt(v1);
		int x2 = stringToInt(v2);
		
		//x1에서 x2까지의 가중치를 주입
		//이 때, x1에서 x2까지의 가중치와 x2에서 x1까지의 가중치는 같다.(중복될 뿐)
		//가중치가 없어서 입력되지 않았다면 기본값 0이 입력되어 있을 것이다.
		weight[x1][x2] = w;
		weight[x2][x1] = w;
	}
	
	public void algorithm(String a) {
		boolean[] visited = new boolean[n]; //각 꼭지점의 방문 여부
		int distance[] = new int[n]; //시작 꼭지점에서부터 각 꼭지점까지의 거리
		
		//시작 꼭지점 a에서부터 각 꼭지점까지의 모든 거리 초기화
		for(int i=0; i<n; i++) {
			//이산수학 교재 251쪽에서는 ∞로 초기화했지만
			//여기에서는 int형의 가장 큰 값 2147483647로 초기화한다.
			distance[i] = Integer.MAX_VALUE;
		}
		
		int x = stringToInt(a); //문자열로 입력된 시작 꼭지점을 해당되는 숫자 인덱스 x로 바꾸고
		distance[x] = 0; //시작 꼭지점 x의 거리를 0으로 초기화하고, 
		visited[x] = true; //방문 꼭지점이므로 true값 저장
		saveRoute[x] = vertex[x]; //★시작 꼭지점의 경로는 자기 자신을 저장
		
		//시작 꼭지점 x에서부터 꼭지점 i까지의 거리를 갱신한다.
		for(int i=0; i<n; i++) {
			//방문하지 않았고 x에서 i까지의 가중치가 존재한다면, 거리 i에 x에서 i까지의 가중치 저장
			//즉 x에서 인접한 꼭지점까지의 거리를 갱신함.
			//(x와 인접하지 않은 꼭지점에는 Integer.MAX_VALUE가 계속 저장되어 있을 것이다.)
			if(!visited[i] && weight[x][i]!=0) {
				distance[i] = weight[x][i];
				saveRoute[i] = vertex[x]; //★시작 꼭지점과 인접한 꼭지점의 경로에 시작 꼭지점을 저장
			}
		}
		
		for(int i=0; i<n-1; i++) {
			int minDistance = Integer.MAX_VALUE; //최단거리 minDistance에 일단 가장 큰 정수로 저장하고,
			int minVertex = -1; //그 거리값이 있는 인덱스 minIndex에 -1을 저장해둔다.
			for(int j=0; j<n; j++) {
				//방문하지 않았고 거리를 갱신한 꼭지점 중에서 가장 가까운 거리와 가장 가까운 꼭지점을 구한다.
				if(!visited[j] && distance[j]!=Integer.MAX_VALUE) {
					if(distance[j]<minDistance) {
						minDistance = distance[j];
						minVertex = j;
					}
				}
			}	
			visited[minVertex] = true; //위의 반복문을 통해 도출된 가장 가까운 꼭지점에 방문 표시
			for(int j=0; j<n; j++) {
				//방문하지 않았고 minVertex와의 가중치가 존재하는(minVertex에서 연결된) 꼭지점이라면
				if(!visited[j] && weight[minVertex][j]!=0) {
					//지금 그 꼭지점이 가지고 있는 거리값이 minVertex와 가중치를 더한 값보다 크다면 최단거리 갱신
					if(distance[j]>distance[minVertex]+weight[minVertex][j]) {
						distance[j] = distance[minVertex]+weight[minVertex][j];
						saveRoute[j] = vertex[minVertex]; //최단거리가 갱신된 꼭지점의 경로에 minVertex에 해당하는 꼭지점 저장
					}
				}
			}
		}
		//시작 꼭지점부터 특정 꼭지점까지의 거리 출력
		for(int i=0; i<n; i++) {
			System.out.println("시작 꼭지점 "+a+"부터 꼭지점 "+vertex[i]+"까지의 거리 :"+distance[i]);
		}
		
		System.out.println("==================================");
		
		//시작 꼭지점부터 특정 꼭지점까지의 경로 출력
		for(int i=0; i<n; i++) {
			String route = "";
			System.out.println("시작 꼭지점 "+a+"부터 꼭지점 "+vertex[i]+"까지의 경로");
			int index = i;
			while(true) {
				if(saveRoute[index] == vertex[index]) break; //시작 꼭지점일 경우 break
				route += " " + saveRoute[index];
				index = stringToInt(saveRoute[index]); //결정적인 역할을 한 꼭지점을 int형으로 바꿔서 index에 저장 
			}
			StringBuilder sb = new StringBuilder(route);
			System.out.println(sb.reverse() + vertex[i]);
		}
	}
}