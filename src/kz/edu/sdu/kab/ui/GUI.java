package kz.edu.sdu.kab.ui;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class GUI extends QMainWindow{

	public GUI(QWidget parent) {
		super(parent);
	}
	
	public void createUI() {
		QWidget widget = new QWidget();
		
		QPushButton okButton = new QPushButton(tr("OK"));
		QVBoxLayout vBox = new QVBoxLayout();
		vBox.addWidget(okButton);
		
		widget.setLayout(vBox);
		
		setCentralWidget(widget);
	}
	
	public static void main(String[] args) {
		QApplication.initialize(args);
		
		GUI aui = new GUI(null);
		aui.setWindowTitle("GUI");
		aui.show();
		
		QApplication.exec();
	}
}
