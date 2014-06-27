package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.AbstractListModel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Font;

@SuppressWarnings("serial")
public class UI extends JFrame{
	private JTextField keyword_text;
	private JTextField url_text;
	private JTextField pageRetrieved_text;
	private JTextField aveWord_text;
	private JTextField aveUrl_text;
	private JTextField aveParseTime_text;
	private JTextField totalRunning_text;
	public UI() {
		super("Webcrawler");
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(255, 250, 240));
		northPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Search keyword(s) (maximum 10):");
		lblNewLabel.setBackground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(lblNewLabel);
		
		JPanel midPanel = new JPanel();
		midPanel.setBackground(new Color(255, 160, 122));
		northPanel.add(midPanel);
		midPanel.setLayout(new GridLayout(1, 3, 0, 3));
		
		JLabel lblKeyword = new JLabel("Keyword:");
		lblKeyword.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblKeyword.setHorizontalAlignment(SwingConstants.CENTER);
		midPanel.add(lblKeyword);
		
		keyword_text = new JTextField();
		midPanel.add(keyword_text);
		keyword_text.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(244, 164, 96));
		midPanel.add(panel_2);
		
		JButton btnAddToList = new JButton("Add to list");
		btnAddToList.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_2.add(btnAddToList);
		
		JPanel botPanel = new JPanel();
		botPanel.setBackground(new Color(255, 250, 240));
		northPanel.add(botPanel);
		botPanel.setLayout(new GridLayout(1, 2, 0, 3));
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblUrl.setBackground(new Color(224, 255, 255));
		lblUrl.setHorizontalAlignment(SwingConstants.CENTER);
		botPanel.add(lblUrl);
		
		url_text = new JTextField();
		botPanel.add(url_text);
		url_text.setColumns(10);
		
		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		centerPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblKeywords = new JLabel("Keywords:");
		lblKeywords.setBounds(26, 11, 88, 14);
		panel.add(lblKeywords);
		
		final JList keywordList = new JList();
		keywordList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(221, 160, 221), new Color(192, 192, 192)));
		keywordList.setVisibleRowCount(10);
		keywordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		keywordList.setBounds(26, 36, 200, 272);
		panel.add(keywordList);
		
		JButton removeButton = new JButton("Remove");
		removeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		removeButton.setBounds(259, 35, 89, 23);
		panel.add(removeButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(224, 255, 255));
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		centerPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblOutputs = new JLabel("Outputs:");
		lblOutputs.setBounds(54, 11, 66, 14);
		panel_1.add(lblOutputs);
		
		JLabel lblPageRetrieved = new JLabel("Page Retrieved:");
		lblPageRetrieved.setBounds(26, 42, 110, 14);
		panel_1.add(lblPageRetrieved);
		
		JLabel lblAveWordPer = new JLabel("Ave. word per page: ");
		lblAveWordPer.setBounds(26, 67, 133, 14);
		panel_1.add(lblAveWordPer);
		
		JLabel lblNewLabel_1 = new JLabel("Ave. URLs per page: ");
		lblNewLabel_1.setBounds(26, 100, 133, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblAverageParseTimepage = new JLabel("Average parse time/page:");
		lblAverageParseTimepage.setBounds(26, 128, 153, 14);
		panel_1.add(lblAverageParseTimepage);
		
		JLabel lblTotalRunningTime = new JLabel("Total Running time:");
		lblTotalRunningTime.setBounds(26, 164, 133, 14);
		panel_1.add(lblTotalRunningTime);
		
		pageRetrieved_text = new JTextField();
		pageRetrieved_text.setEditable(false);
		pageRetrieved_text.setBounds(206, 36, 103, 20);
		panel_1.add(pageRetrieved_text);
		pageRetrieved_text.setColumns(10);
		
		aveWord_text = new JTextField();
		aveWord_text.setEditable(false);
		aveWord_text.setBounds(206, 64, 103, 20);
		panel_1.add(aveWord_text);
		aveWord_text.setColumns(10);
		
		aveUrl_text = new JTextField();
		aveUrl_text.setEditable(false);
		aveUrl_text.setBounds(206, 94, 103, 20);
		panel_1.add(aveUrl_text);
		aveUrl_text.setColumns(10);
		
		aveParseTime_text = new JTextField();
		aveParseTime_text.setEditable(false);
		aveParseTime_text.setBounds(206, 125, 103, 20);
		panel_1.add(aveParseTime_text);
		aveParseTime_text.setColumns(10);
		
		totalRunning_text = new JTextField();
		totalRunning_text.setEditable(false);
		totalRunning_text.setBounds(206, 158, 103, 20);
		panel_1.add(totalRunning_text);
		totalRunning_text.setColumns(10);
		
		JList keyword_display = new JList();
		keyword_display.setBorder(new LineBorder(new Color(153, 50, 204), 2, true));
		keyword_display.setVisibleRowCount(10);
		keyword_display.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyword_display.setModel(new AbstractListModel() {
			String[] values = new String[] {"Keywords"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		keyword_display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		keyword_display.setBounds(26, 199, 110, 240);
		panel_1.add(keyword_display);
		
		JList hitperpage_list = new JList();
		hitperpage_list.setBorder(new LineBorder(new Color(153, 50, 204), 2, true));
		hitperpage_list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Ave hits/page"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		hitperpage_list.setVisibleRowCount(10);
		hitperpage_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hitperpage_list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hitperpage_list.setBounds(141, 199, 110, 240);
		panel_1.add(hitperpage_list);
		
		JList list = new JList();
		list.setBorder(new LineBorder(new Color(153, 50, 204), 2, true));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Total hits"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list.setBounds(256, 200, 110, 240);
		panel_1.add(list);
	}
	public void init() {
		this.setPreferredSize(new Dimension(800, 600));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
