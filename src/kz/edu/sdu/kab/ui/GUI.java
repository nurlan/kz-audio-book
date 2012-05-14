package kz.edu.sdu.kab.ui;

import kz.edu.sdu.kab.parser.Parser;

import com.trolltech.qt.core.QDir;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.webkit.QWebView;

public class GUI extends QMainWindow{

	private QMenu fileMenu;
    private QMenu helpMenu;

    private QAction openAct;
    private QAction exitAct;
    private QAction aboutAct;
    private QAction aboutQtJambiAct;
    
    private QWidget centerWidget;
    
    private QVBoxLayout mainLayout;
    private QHBoxLayout topLayout;
    private QVBoxLayout centerLayout;
    private QHBoxLayout bottomLayout;
    
    private QWebView webView;
    
    private Parser parser;
    
	public GUI(QWidget parent) {
		super(parent);
		createActions();
		createMenus();
		createUI();
	}
	
	private void createMenus()
    {
        fileMenu = menuBar().addMenu(tr("&File"));
        fileMenu.addAction(openAct);
        fileMenu.addAction(exitAct);

        helpMenu = menuBar().addMenu(tr("&Help"));
        helpMenu.addAction(aboutAct);
        helpMenu.addAction(aboutQtJambiAct);
    }
	
	private void createActions()
    {
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
    	
    	webView = new QWebView();
    	webView.setHtml("Html text!");
    	
    	centerLayout.addWidget(webView);
    	resize(800, 500);
	}
	
	protected void open() {
		String path = QFileDialog.getOpenFileName(this,tr("Open a new book"),QDir.currentPath(),new QFileDialog.Filter(tr("Documents(*.doc *.docx);;PDF(*.pdf)")));
		if (path.length() != 0) {
			parser = new Parser();
			String content = parser.getFileContent(path);
			webView.setHtml(content);
		}
	}
	protected void about() {
        QMessageBox.information(this, "Info", "It's your turn now :-)");
    }
	
	public static void main(String[] args) {
		QApplication.initialize(args);
		
		GUI aui = new GUI(null);
		aui.setWindowTitle("KZ Audio book");
		aui.show();
		
		QApplication.exec();
	}
}
