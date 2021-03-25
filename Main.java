import java.awt.*;
import javax.swing.*;
import java.io.*;

public class Main{
  public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
						Runner.createAndShowGUI();
				}
		});
	}
}