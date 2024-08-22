package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HeifConverter {

    public static void main(String[] args) {
        // 入力HEIFファイルのパス
        String inputFilePath = "src/main/resources/input.heic";
        // 出力JPEGファイルのパス
        String outputFilePathJpeg = "output.jpeg";

        // JPEGへの変換
        boolean jpegSuccess = convertHeifToFormat(inputFilePath, outputFilePathJpeg);
        if (jpegSuccess) {
            System.out.println("HEIFファイルがJPEGに変換されました: " + outputFilePathJpeg);
        } else {
            System.out.println("JPEGへの変換に失敗しました。");
        }
    }

    private static boolean convertHeifToFormat(String inputFilePath, String outputFilePath) {
        try {
            // FFmpegコマンドの構築
            String[] command = {
                "ffmpeg", "-y", "-i", inputFilePath, outputFilePath
            };

            // プロセスの開始
            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();

            // FFmpegの出力を読み取る
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // プロセスの終了を待機
            int exitCode = process.waitFor();
            return exitCode == 0;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
