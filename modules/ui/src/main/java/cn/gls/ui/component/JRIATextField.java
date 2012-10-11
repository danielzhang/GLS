package cn.gls.ui.component;

import javax.swing.JTextField;
/**
 * 
 * @ClassName JRIATextField.java
 * @Createdate 2012-7-29
 * @Description textfield含有下拉框
 * @Version 1.0
 * @Update 2012-7-29 
 * @author "Daniel Zhang"
 *
 */
public class JRIATextField extends JTextField {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6461498428146995767L;
	private static final int MAX_VISIBLE_ROWS = 8;
    private final java.util.List<String> history = new java.util.ArrayList<String>();
    private final javax.swing.JPopupMenu popup = new javax.swing.JPopupMenu() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 8661855516756537640L;

		public java.awt.Dimension getPreferredSize() {
            java.awt.Dimension dimension = super.getPreferredSize();

            dimension.width = JRIATextField.this.getWidth();

            return dimension;
        }
    };
     private final javax.swing.JList list = new javax.swing.JList(new javax.swing.DefaultListModel());
    private String userText;
    private boolean notificationDenied;

    public JRIATextField() {
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(list,
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBar(null);
        scrollPane.setBorder(null);

        list.setFocusable(false);

        popup.add(scrollPane);
        popup.setFocusable(false);
        popup.setBorder(new javax.swing.border.LineBorder(java.awt.Color.BLACK, 1));

        getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                onTextChanged();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                onTextChanged();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                onTextChanged();
            }
        });

        list.addMouseMotionListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());//返回最接近列表的坐标系统中给定位置的单元索引

                if (index >= 0 && list.getSelectedIndex() != index) {
                    list.setSelectedIndex(index);
                }
            }
        });

        list.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    setTextWithoutNotification((String) list.getSelectedValue());

                    popup.setVisible(false);
                }
            }
        });

        addFocusListener(new java.awt.event.FocusAdapter() {//控件失去焦点关闭菜单

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                popup.setVisible(false);
            }
        });

        addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (popup.isShowing()) {
                    switch (e.getKeyCode()) {
                        case java.awt.event.KeyEvent.VK_UP: {
                            changeListSelectedIndex(-1);
                            break;
                        }
                        case java.awt.event.KeyEvent.VK_PAGE_UP: {
                            //System.out.println(list.getVisibleRowCount());
                            changeListSelectedIndex(-list.getVisibleRowCount());
                            break;
                        }
                        case java.awt.event.KeyEvent.VK_DOWN: {
                            changeListSelectedIndex(1);
                            break;
                        }
                        case java.awt.event.KeyEvent.VK_PAGE_DOWN: {
                            //System.out.println(list.getVisibleRowCount());
                            changeListSelectedIndex(list.getVisibleRowCount());
                            break;
                        }
                        case java.awt.event.KeyEvent.VK_ESCAPE: {
                            popup.setVisible(false);
                            setTextWithoutNotification(userText);
                            break;
                        }
                        case java.awt.event.KeyEvent.VK_ENTER:
                        case java.awt.event.KeyEvent.VK_LEFT:
                        case java.awt.event.KeyEvent.VK_RIGHT: {
                            popup.setVisible(false);
                            break;
                        }
                    }
                } else {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN ||
                            e.getKeyCode() == java.awt.event.KeyEvent.VK_UP ||
                            e.getKeyCode() == java.awt.event.KeyEvent.VK_PAGE_UP ||
                            e.getKeyCode() == java.awt.event.KeyEvent.VK_PAGE_DOWN) {
                        userText = getText();

                        showFilteredHistory();
                    }
                }
            }
        });
    }

    private void changeListSelectedIndex(int delta) {
        int size = list.getModel().getSize();//返回此列表中的组件数。
        int index = list.getSelectedIndex();//返回最小的选择单元索引.只选择了列表中单个项时，返回该选择。
        //选择了多项时，则只返回最小的选择索引。如果什么也没有选择，则返回 -1。

        int newIndex;

        if (index < 0) {
            newIndex = delta > 0 ? 0 : size - 1;
        } else {
            newIndex = index + delta;
        }

        if (newIndex >= size || newIndex < 0) {
            newIndex = newIndex < 0 ? 0 : size - 1;

            if (index == newIndex) {
                newIndex = -1;
            }
        }

        if (newIndex < 0) {
            list.getSelectionModel().clearSelection();
            list.ensureIndexIsVisible(0);//滚动封闭视口中的列表，使指定单元完全可见。

            setTextWithoutNotification(userText);
        } else {
            list.setSelectedIndex(newIndex);
            list.ensureIndexIsVisible(newIndex);

            setTextWithoutNotification((String) list.getSelectedValue());
        //返回最小的选择单元索引的值；只选择了列表中单个项时，返回所选值。选择了多项时，返回最小的选择索引的值。如果什么也没有选择，则返回 null
        }
    }

    private void setTextWithoutNotification(String text) {
        notificationDenied = true;

        try {
            setText(text);
        } finally {
            notificationDenied = false;
        }
    }

    private void onTextChanged() {
        if (!notificationDenied) {
            userText = getText();

            showFilteredHistory();
        }
    }

    public void showFilteredHistory() {
        list.getSelectionModel().clearSelection();

        javax.swing.DefaultListModel model = (javax.swing.DefaultListModel) list.getModel();

        model.clear();

        for (String s : history) {
            if (userText!=null&&s.contains(userText)) {
       
                model.addElement(s);
            }
        }

        int size = model.size();

        if (size == 0) {
            popup.setVisible(false);
        } else {
            list.setVisibleRowCount(size < MAX_VISIBLE_ROWS ? size : MAX_VISIBLE_ROWS);

            popup.pack();

            if (!popup.isShowing()) {
                popup.show(JRIATextField.this, 0, getHeight());
            }
        }
    }

    public java.util.List<String> getHistory() {
        return java.util.Collections.unmodifiableList(history);
    }

    public void setHistory(java.util.List<? extends String> history) {
        this.history.clear();
        if(history!=null)
        this.history.addAll(history);
        if(userText==null)
            userText="";
        showFilteredHistory();
    }
}
