package tp4_reseau;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FenetreChat extends JFrame {

	private JTextArea zoneDiscussion;
	private JTextField champEnvoi;

	public FenetreChat(String titre) throws IOException {

		super(titre);

		setBounds(600, 300, 400, 400);

		Container conteneur = getContentPane();
		conteneur.setLayout(new BorderLayout(20, 20));

		zoneDiscussion = new JTextArea();
		conteneur.add(zoneDiscussion, BorderLayout.CENTER);

		JScrollPane barreDefilement = new JScrollPane(zoneDiscussion);
		conteneur.add(barreDefilement);

		zoneDiscussion.setEditable(false);

		champEnvoi = new JTextField();
		conteneur.add(champEnvoi, BorderLayout.SOUTH);

		setVisible(true);

	}

	public void afficherMessage(String msg) {
		zoneDiscussion.append(msg + "\n");
	}

	public void effacerChampEnvoi() {
		champEnvoi.setText("");
	}

	public String getMessage() {
		return champEnvoi.getText();
	}

	public void capturerEntree(KeyListener listener) {
		champEnvoi.addKeyListener(listener);
	}
	
	public void gererFermeture(WindowListener listener) {
		addWindowListener(listener);
	}

}
