package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import model.KeyWord;
import controller.WebCrawler;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class WebCrawlerUI extends JFrame{

	private JTextField keyword_text;
	private JTextField url_text;
	private JTextField pageRetrieved_text;
	private JTextField aveWord_text;
	private JTextField aveUrl_text;
	private JTextField aveParseTime_text;
	private JTextField totalRunning_text;
	private JList<KeyWord> my_key_JList;
	/**
	 * This is the string from user input.
	 * from keyword_text.getText.
	 */
	private String my_words;
	/**
	 * Need to use DefaultListModel in order to Add or remove from list, since
	 * JList doesnt have add or remove method.
	 * This is the List model for Keyword list which being displayed on the left side panel.
	 */
	private DefaultListModel<KeyWord> my_key_left_model;
	//private DefaultListModel<KeyWord> my_key_right_model;
	private DefaultListModel<Double> my_ave_hit_model;
	private DefaultListModel<Integer> my_total_hit_model;

	private WebCrawler webCrawler;
	private Set<KeyWord> searchKeys;

	public WebCrawlerUI() {
		super("Webcrawler");
		webCrawler = new WebCrawler();
		searchKeys = new HashSet<KeyWord>();

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

		//************************************************************************************ Add to list button**************//
		btnAddToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//maximum 10 keywords to be added to the JList.
				if (my_key_left_model.size() < 10) {
					my_words = keyword_text.getText(); //get the text from user input keyword.
					final KeyWord search_key = new KeyWord(my_words);
					int prevSize = webCrawler.getKeyWords().size();
					webCrawler.addKeyWord(search_key);
					if (webCrawler.getKeyWords().size() != prevSize) {
						//add this into the list to display only if its not a duplicate
						my_key_left_model.addElement(search_key); 
					}
					keyword_text.setText("");
					System.out.println(webCrawler.getKeyWords());
				}
				else
					System.out.println("Can't add anymore keyword, maximum is 10 words.");
			}
		});
		btnAddToList.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_2.add(btnAddToList);

		JPanel botPanel = new JPanel();
		botPanel.setBackground(new Color(255, 250, 240));
		northPanel.add(botPanel);
		botPanel.setLayout(new GridLayout(1, 3, 0, 3));

		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblUrl.setBackground(new Color(224, 255, 255));
		lblUrl.setHorizontalAlignment(SwingConstants.CENTER);
		botPanel.add(lblUrl);

		url_text = new JTextField();
		botPanel.add(url_text);
		url_text.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 250, 240));
		botPanel.add(panel_3);
		
		JComboBox threadComboBox = new JComboBox();
		threadComboBox.setBackground(Color.WHITE);
		threadComboBox.setModel(new DefaultComboBoxModel(new String[] {"Single Thread", "Multi Thread"}));
		panel_3.add(threadComboBox);

		JButton crawlButton = new JButton("Crawl");
		crawlButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_3.add(crawlButton);

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
		//*************************************************************************************fixing JList ************************//
		//http://www.seasite.niu.edu/cs580java/JList_Basics.htm

		my_key_left_model = new DefaultListModel<KeyWord>();
		my_key_JList = new JList<KeyWord>(my_key_left_model);
		my_key_JList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(221, 160, 221), new Color(192, 192, 192)));
		my_key_JList.setVisibleRowCount(10);
		my_key_JList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		my_key_JList.setBounds(26, 36, 200, 272);
		panel.add(my_key_JList);

		crawlButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("------------------------------------");
				//TODO clearContents not working
				webCrawler.clearContents();
				
				//Make sure that the URL that user type in is valid: 
				if (webCrawler.isValidURL(url_text.getText())) {

					webCrawler.setBeginURL(url_text.getText());
					webCrawler.setSearchKeyWords(searchKeys);
					System.out.println("searchKeys: " + webCrawler.getKeyWords());
					System.out.println("beginURL: " + webCrawler.getURLs());
					webCrawler.startSingleThread();
					System.out.println(webCrawler.getWebSitesCrawled());

					//display into text boxes: 
					pageRetrieved_text.setText(""+webCrawler.getWebSitesCrawled().size());
					aveWord_text.setText("" + webCrawler.getAvgWordPerPage());
					aveUrl_text.setText("" + webCrawler.getAvgURLsPerPage());

					//This is the Average parse time per page: 
					double time = (webCrawler.totalNanoTime())/(webCrawler.getWebSitesCrawled().size());
					aveParseTime_text.setText("" + time);
					totalRunning_text.setText(""+ webCrawler.totalNanoTime() );

					//Display on JList: 
					// by looping thru the key word list model, get the stat for each word and display in JList.
					
					for (int i = 0; i < my_key_left_model.size(); i ++){
						my_total_hit_model.addElement(webCrawler.getWordTotalHits(my_key_left_model.elementAt(i)));
						double ave = (webCrawler.getWordTotalHits(my_key_left_model.elementAt(i)))/(webCrawler.getWebSitesCrawled().size());
						my_ave_hit_model.addElement(ave);
					}

				}
				else
				{
					//throw a pop up warning if invalid url
					JOptionPane.showMessageDialog(null, "Invalid URL, please retry.");
				}
			}
		});

		JButton removeButton = new JButton("Remove");
		//******************************************************************************Remove Button *******************//
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = my_key_JList.getSelectedIndex();
				if (index > -1) {
					webCrawler.removeKeyWord(my_key_left_model.remove(index));
					System.out.println("searchKeys (after remove): " + webCrawler.getKeyWords());
				}
				System.out.println(webCrawler.getKeyWords());
			}
		});
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

		JLabel lblTotalRunningTime = new JLabel("Total Running time (ns):");
		lblTotalRunningTime.setBounds(26, 164, 153, 14);
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
		aveParseTime_text.setBounds(206, 125, 133, 20);
		panel_1.add(aveParseTime_text);
		aveParseTime_text.setColumns(10);

		totalRunning_text = new JTextField();
		totalRunning_text.setEditable(false);
		totalRunning_text.setBounds(206, 158, 133, 20);
		panel_1.add(totalRunning_text);
		totalRunning_text.setColumns(10);

		//my_key_right_model = new DefaultListModel<KeyWord>();
		JList<KeyWord> keyword_display = new JList<KeyWord>(my_key_left_model);
		keyword_display.setBorder(new LineBorder(new Color(153, 50, 204), 2, true));
		keyword_display.setVisibleRowCount(10);
		keyword_display.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyword_display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		keyword_display.setBounds(26, 214, 110, 240);
		panel_1.add(keyword_display);

		///////////////////////// Hit per page JList:

		my_ave_hit_model = new DefaultListModel<Double>();
		JList<Double> hitperpage_list = new JList<Double>(my_ave_hit_model);
		hitperpage_list.setBorder(new LineBorder(new Color(153, 50, 204), 2, true));


		hitperpage_list.setVisibleRowCount(10);
		hitperpage_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hitperpage_list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hitperpage_list.setBounds(141, 214, 110, 240);
		panel_1.add(hitperpage_list);


		////////////////////// Total hits JList: 
		my_total_hit_model = new DefaultListModel<Integer>();
		JList<Integer> totalhits_display = new JList<Integer>(my_total_hit_model);
		totalhits_display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		totalhits_display.setBorder(new LineBorder(new Color(153, 50, 204), 2, true));



		totalhits_display.setFont(new Font("Tahoma", Font.PLAIN, 12));
		totalhits_display.setBounds(256, 215, 110, 240);
		panel_1.add(totalhits_display);

		JLabel lblKeywords_1 = new JLabel("Keywords");
		lblKeywords_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKeywords_1.setBounds(41, 189, 77, 14);
		panel_1.add(lblKeywords_1);

		JLabel lblAveHit = new JLabel("Ave. hit/page");
		lblAveHit.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAveHit.setBounds(141, 189, 103, 14);
		panel_1.add(lblAveHit);

		JLabel lblTotalHits = new JLabel("Total Hits");
		lblTotalHits.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTotalHits.setBounds(282, 190, 77, 14);
		panel_1.add(lblTotalHits);
	}
	public void init() {
		this.setPreferredSize(new Dimension(800, 620));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	public String getURL() {
		return url_text.getText();
	}
}
