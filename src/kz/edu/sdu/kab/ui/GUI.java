package kz.edu.sdu.kab.ui;

import kz.edu.sdu.kab.AudioBook;
import kz.edu.sdu.kab.config.XmlAudioBookConfig;
import kz.edu.sdu.kab.parser.Parser;

import com.trolltech.qt.core.QDir;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.webkit.QWebView;

public class GUI extends QMainWindow {

	private QMenu fileMenu;
	private QMenu helpMenu;

	private QAction openAct;
	private QAction exitAct;
	private QAction aboutAct;
	private QAction aboutQtJambiAct;

	private QVBoxLayout mainLayout;
	private QHBoxLayout topLayout;
	private QVBoxLayout centerLayout;
	private QHBoxLayout bottomLayout;

	private QWebView webView;
	
	private QWidget centerWidget;
	private QPushButton playButton;
	private QPushButton stopButton;
	private QPushButton generateButton;
	
	private XmlAudioBookConfig audioBookConfig;
	private Parser parser;
	private AudioBook audioBook;
	
	private String content;
	private String audioFileName;
	
	public GUI(QWidget parent) {
		super(parent);
		createActions();
		createMenus();
		createUI();
		
		audioBookConfig = new XmlAudioBookConfig();
		audioBook = new AudioBook();
	}

	private void createMenus() {
		fileMenu = menuBar().addMenu(tr("&File"));
		fileMenu.addAction(openAct);
		fileMenu.addAction(exitAct);

		helpMenu = menuBar().addMenu(tr("&Help"));
		helpMenu.addAction(aboutAct);
		helpMenu.addAction(aboutQtJambiAct);
	}

	private void createActions() {
		openAct = new QAction(tr("&Open"), this);
		openAct.setShortcut(tr("Ctrl+O"));
		openAct.setStatusTip(tr("Open a new book"));
		openAct.triggered.connect(this, "open()");

		exitAct = new QAction(tr("E&xit"), this);
		exitAct.setShortcut(tr("Ctrl+Q"));
		exitAct.setStatusTip(tr("Exit the application"));
		exitAct.triggered.connect(this, "close()");

		aboutAct = new QAction(tr("&About"), this);
		aboutAct.setStatusTip(tr("Show the application's About box"));
		aboutAct.triggered.connect(this, "about()");

		aboutQtJambiAct = new QAction(tr("About &Qt Jambi"), this);
		aboutQtJambiAct.setStatusTip(tr("Show the Qt Jambi's About box"));
		aboutQtJambiAct.triggered.connect(QApplication.instance(), "aboutQtJambi()");
	}

	private void createUI() {
		centerWidget = new QWidget();
		setCentralWidget(centerWidget);

		mainLayout = new QVBoxLayout();
		centerWidget.setLayout(mainLayout);

		topLayout = new QHBoxLayout();
		centerLayout = new QVBoxLayout();
		bottomLayout = new QHBoxLayout();

		mainLayout.addLayout(topLayout);
		mainLayout.addLayout(centerLayout);
		mainLayout.addLayout(bottomLayout);
	    
		QHBoxLayout controlLayout = new QHBoxLayout();
		
		playButton = new QPushButton(tr("play"));
		stopButton = new QPushButton(tr("stop"));
		generateButton = new QPushButton(tr("generate"));
		
		playButton.setToolTip(tr("Play"));
		stopButton.setToolTip(tr("Stop"));
		generateButton.setToolTip(tr("Text processing"));
		
		controlLayout.addWidget(generateButton);
		controlLayout.addStretch();
		controlLayout.addWidget(playButton);
		controlLayout.addWidget(stopButton);
		
		topLayout.addLayout(controlLayout);
		
		webView = new QWebView();
		
		centerLayout.addWidget(webView);
		resize(800, 500);
		
		//signals
		playButton.clicked.connect(this, "play()");
		stopButton.clicked.connect(this, "stop()");
		generateButton.clicked.connect(this, "generate()");
	}

	protected void open() {
		String path = QFileDialog.getOpenFileName(this, tr("Open a new book"),
				QDir.currentPath(), new QFileDialog.Filter(
				tr("Documents(*.doc *.docx);;PDF(*.pdf)")));
		if (path.length() != 0) {
			parser = new Parser();
			content = parser.getFileContent(path);
			String htmlContent = parser.getFileHtmlContent(path);
			
			webView.setHtml(htmlContent);
		}
	}
	
	protected void generate() {
		if(content != null && content.length() != 0) {
			audioFileName = audioBook.make(content,"wav");
		}
		else {
			QMessageBox.warning(this, "Warning", "In order to text processing, you should choose some file with kazakh text");
		}
	}
	
	protected void play() {
		if(content != null && content.length() != 0) {
			if( audioFileName == null) {
				audioFileName = audioBook.make(content, "wav");
			}
			audioBook.playWAV(audioFileName);
		}
		else {
			QMessageBox.warning(this, "Warning", "In order to play, you should choose some file with kazakh text");
		}
	}
	
	protected void stop() {
		if(audioBook != null) {
			audioBook.stop();
		}
	}

	protected void about() {
		QMessageBox.information(this, "Info", "It's your turn now :-)");
	}

	public static void main(String[] args) {
		QApplication.initialize(args);

		GUI gui = new GUI(null);
		gui.setWindowTitle("Қазақша Audio book");
		gui.show();

		Thread t = new Thread(new AUI(gui));
		t.start();
		
		QApplication.exec();
	}
}
