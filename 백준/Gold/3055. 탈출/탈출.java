import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static final int ROCK = -2;
    static final int WATER = -1;
    static final int EMPTY = 0;
    static final int GO = 1;
    static final int ARRIVE = Integer.MAX_VALUE;



    //                      위 오른쪽 아래 왼쪽
    static final int tx[] = {-1, 0, 1, 0};
    static final int ty[] = {0, 1, 0, -1};
    static int r, c;
    static int map[][];
    static int isVisited[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        r = Integer.parseInt(stk.nextToken());
        c = Integer.parseInt(stk.nextToken());

        map = new int[r][c];
        isVisited = new int[r][c];

        Point gp = null, ap = null;
        ArrayList<Point> wp = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            String line = br.readLine();
            for (int j = 0; j < map[i].length; j++) {
                char c = line.charAt(j);
                int insert = 0;
                switch (c) {
                    case '.':
                        insert = EMPTY;
                        break;
                    case '*':
                        insert = WATER;
                        wp.add(new Point(i, j));
                        break;
                    case 'X':
                        insert = ROCK;
                        break;
                    case 'D':
                        insert = ARRIVE;
                        ap = new Point(i, j);
                        break;
                    case 'S':
                        insert = GO;
                        gp = new Point(i, j);
                        break;
                }
                map[i][j] = insert;

            }
        }

        bfs(gp, wp, ap);

        if(map[ap.x][ap.y] == Integer.MAX_VALUE)
            System.out.println("KAKTUS");
        else
            System.out.println(map[ap.x][ap.y]);

        br.close();
    }

    public static void bfs(Point gp, ArrayList<Point> wp, Point ap) {
        /**
         * 물부터 이동한 후 고슴도치 이동
         * 1. 물
         *  물은 인접한 네칸으로 번짐
         *  물은 돌과 비버집 통과 불가
         *  물은 고슴도치가 있는 장소로도 이동 가능
         *
         * 2. 고슴도치
         *  고는 인접한 네칸으로 이동
         *  물이 찰 예정인 곳으로 이동 불가
         */
        Queue<int[]> q = new LinkedList<>();
        for (Point p : wp) {
            q.add(new int[]{p.x, p.y, WATER});
        }
        q.add(new int[]{gp.x, gp.y, 0});

        while (!q.isEmpty()) {
            int[] v = q.poll();
            map[v[0]][v[1]] = v[2];
            if (v[0] == ap.x && v[1] == ap.y) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int mx = v[0] + tx[i];
                int my = v[1] + ty[i];

                if (0 <= mx && mx < r && 0 <= my && my < c) {
                    int plan = map[mx][my];
                    if (v[2] == WATER) {
                        if (plan == EMPTY || plan == GO) {
                            q.add(new int[]{mx, my, WATER});
                            map[mx][my] = WATER;
                        }
                    } else {
                        if (plan == EMPTY || plan == ARRIVE) {
                            q.add(new int[]{mx, my, v[2] + 1});
                            map[mx][my] = v[2] + 1;
                        }
                    }
                }
            }
//            for (int[] ints : map) {
//                for (int i : ints) {
//                    System.out.print(i + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
        }
    }

}

