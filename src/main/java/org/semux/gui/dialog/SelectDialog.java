package org.semux.gui.dialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.semux.gui.Action;
import org.semux.gui.SwingUtil;
import org.semux.utils.UnreachableException;

public class SelectDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JComboBox<Object> comboBox;
    private int selected = -1;

    public SelectDialog(JFrame parent, String message, List<? extends Object> options) {
        super(parent, "Select");
        this.setModal(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel labelLogo = new JLabel("");
        labelLogo.setIcon(SwingUtil.loadImage("logo", 96, 96));

        JLabel lblMessage = new JLabel(message);

        JButton btnOk = new JButton("OK");
        btnOk.setSelected(true);
        btnOk.setActionCommand(Action.OK.name());
        btnOk.addActionListener(this);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setActionCommand(Action.CANCEL.name());
        btnCancel.addActionListener(this);

        comboBox = new JComboBox<Object>();
        comboBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        for (Object opt : options) {
            comboBox.addItem(opt);
        }
        comboBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(SelectDialog.this, 0, Action.OK.name()));
                }
            }
        });

        // @formatter:off
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelLogo)
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblMessage)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                            .addGap(101)
                            .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(17))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(12)
                            .addComponent(labelLogo))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(21)
                            .addComponent(lblMessage)
                            .addGap(18)
                            .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(16)
                            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(btnOk)
                                .addComponent(btnCancel))))
                    .addContainerGap(20, Short.MAX_VALUE))
        );
        getContentPane().setLayout(groupLayout);
        // @formatter:on

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Action action = Action.valueOf(e.getActionCommand());

        switch (action) {
        case OK:
            selected = comboBox.getSelectedIndex();
            break;
        case CANCEL:
            selected = -1;
            break;
        default:
            throw new UnreachableException();
        }

        this.dispose();
    }

    public int getSelectedIndex() {
        this.setVisible(true);
        return selected;
    }

    public static void main(String[] args) throws InterruptedException {
        String msg = "Please select an account to continue";
        List<String> options = Arrays.asList("0x1122334455667788112233445566778811223344, #0",
                "0x1122334455667788112233445566778811223344, #1");

        SelectDialog select = new SelectDialog(null, msg, options);
        int idx = select.getSelectedIndex();
        System.out.println("Selected: " + idx);
    }
}
