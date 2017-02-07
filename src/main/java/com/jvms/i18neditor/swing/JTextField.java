package com.jvms.i18neditor.swing;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.undo.UndoManager;

import com.jvms.i18neditor.LookAndFeel;
import com.jvms.i18neditor.swing.text.JTextComponentMenuListener;

/**
 * This class extends a default {@link javax.swing.JTextField} with a {@link UndoManager},
 * a right click menu and a custom look and feel.
 * 
 * @author Jacob
 */
public class JTextField extends javax.swing.JTextField {
	private final static long serialVersionUID = 5296894112638304738L;
	protected final UndoManager undoManager = new UndoManager();
	
	/**
	 * Constructs a {@link JTextField}.
	 */
	public JTextField() {
		this(null);
	}
	
	/**
	 * Constructs a {@link JTextField} with an initial text.
	 */
	public JTextField(String text) {
		super(text, 25);
		
		Border border = BorderFactory.createLineBorder(LookAndFeel.BORDER_COLOR);
		setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(6,8,6,8)));
		getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));
		
		// Add undo support
		getActionMap().put("undo", new UndoAction(undoManager));
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "undo");
		
		// Add redo support
		getActionMap().put("redo", new RedoAction(undoManager));
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "redo");
		
		// Add popup menu support
		addMouseListener(new JTextComponentMenuListener(this, undoManager));
	}
}
