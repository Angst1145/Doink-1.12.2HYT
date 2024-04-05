package tomk.utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class QQLogo {
    public static String Name = "tomk";
    public static String version = "Build 2023";
    public static String username;
    public static boolean isStarting;
    public static void sendWindowsMessageLogin() throws AWTException, IOException {
        String AT = JOptionPane.showInputDialog("请输入QQ号");
        QQLogo.username = AT;

        QQLogo.isStarting = true;

        try {
            if (username == null) {
                JOptionPane.showMessageDialog(null, "QQ号不能为空!", "Logoget", 0);
                System.exit(0);
            }
        } finally {

        }
    }}