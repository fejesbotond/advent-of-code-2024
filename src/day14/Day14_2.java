package day14;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import static day14.Day14_1.*;

public class Day14_2 {

    static void step(List<int[]> robots, int[][] grid){
        for(int[] r : robots){
            int px = r[0], py = r[1], vx = r[2], vy = r[3];
            px += vx;
            py += vy;
            px = px % m;
            py = py % n;
            if(px<0) px+=m;
            if(py<0) py+=n;
            grid[py][px]++;
            grid[r[1]][r[0]]--;
            r[0] = px;
            r[1] = py;
        }
    }
    static void initGrid(List<int[]> robots, int[][] grid){
        robots.forEach(r -> grid[r[1]][r[0]]++);
    }
    public static void generateAndSaveImg(int[][] mask, String fileName) throws IOException {
        int height = mask.length;
        int width = mask[0].length;
        var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int black = Color.BLACK.getRGB();
        int white = Color.WHITE.getRGB();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, mask[y][x] > 0 ? black : white);
            }
        }
        var file = new File(fileName);
        ImageIO.write(img, "PNG", file);
        System.out.println("Saved: " + file.getAbsolutePath());
    }

    public static void main(String[] args) throws IOException {
        var robots = readInput();
        var grid = new int[n][m];
        initGrid(robots, grid);
        for(int i = 0; i < 10000; i++){
            generateAndSaveImg(grid, i + ".png");
            step(robots, grid);
        }
    }
}