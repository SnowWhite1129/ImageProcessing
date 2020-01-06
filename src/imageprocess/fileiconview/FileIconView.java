package imageprocess.fileiconview;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

public class FileIconView extends FileView {
	private FileNameExtensionFilter fliter;
	private Icon icon;

	public FileIconView(FileNameExtensionFilter filter, Icon anIcon) {
		fliter = filter;
		icon = anIcon;
	}

	public Icon getIcon(File f) {
		if (f.isDirectory() && fliter.accept(f)) {
			return icon;
		} else {
			return null;
		}
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}
}
