package ui;

import function.FileManager;
import function.Helper;
import model.Balance;
import model.ListOfRecords;
import ui.dialogs.AddDialog;
import ui.dialogs.ChartDialog;
import ui.dialogs.EditDialog;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements Observer {
	private final String[] TABLE_COLUMNS = {"Date", "Amount", "Description", "Category"};
	private final int DIALOG_WIDTH = 400;
	private final int DIALOG_HEIGHT = 400;
	private final Dimension MAIN_FRAME_DIM = new Dimension(400, 600);
	private final int PANEL_WIDTH = (int) (MAIN_FRAME_DIM.width * 0.97);
	private final Dimension BUTTON_DIM = new Dimension(80, 30);

	private final Rectangle RECORD_PANEL_RECT = new Rectangle(0, 0, PANEL_WIDTH, (int) (MAIN_FRAME_DIM.height * 0.65));
	private final Rectangle BALANCE_PANEL_RECT = new Rectangle(0, RECORD_PANEL_RECT.height + 5, PANEL_WIDTH, (int)(MAIN_FRAME_DIM.height * 0.15));
	private final Rectangle BUTTON_PANEL_RECT = new Rectangle(0, RECORD_PANEL_RECT.height + BALANCE_PANEL_RECT.height + 10, PANEL_WIDTH, (int)(MAIN_FRAME_DIM.height * 0.15));

	private DefaultTableModel recordTableModel = new DefaultTableModel(TABLE_COLUMNS, 0);
	private ListOfRecords records = new ListOfRecords();
	private AddDialog addDialog;
	private EditDialog editDialog;
	private JTable recordTable;
	private JLabel incomeMoney, expenseMoney, balanceMoney;
	private FileManager fm = new FileManager();
	private Balance balance = new Balance(records);
	private Helper helper = Helper.getInstance();
	private ChartDialog chartDialog;

	public static void main(String[] args) {
		MainFrame m = new MainFrame();
		m.loadGUI();
	}

	private void loadGUI() {
		//Main Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Piggy Manager");
		setLocation(100, 100);
		setPreferredSize(MAIN_FRAME_DIM);
		setLayout(null);

		setupButtonPanel();
		setupTablePanel();
		setupBalancePanel();

		//Dialog Popups
		addDialog = new AddDialog(records);
		editDialog = new EditDialog(records);
		chartDialog = new ChartDialog(records);
		addDialog.addObserver(this);
		editDialog.addObserver(this);
		pack();
		setVisible(true);
	}

	private void setupButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		helper.attach(this, buttonPanel, BUTTON_PANEL_RECT);
		//Buttons
		ListeningButton addBtn = new ListeningButton("Add", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setupAddDialog();
			}
		}, BUTTON_DIM);
		ListeningButton editBtn = new ListeningButton("Edit", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setupEditDialog();
			}
		}, BUTTON_DIM);
		ListeningButton saveBtn = new ListeningButton("Save", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fm.saveFunc(records.getRecords());
			}
		}, BUTTON_DIM);
		ListeningButton loadBtn = new ListeningButton("Load", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fm.loadFunc(records.getRecords());
				update();
			}
		}, BUTTON_DIM);
		ListeningButton chartBtn = new ListeningButton("Chart", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setupChartDialog();
					}
				});
			}
		}, BUTTON_DIM);

		buttonPanel.add(addBtn.getButton());
		buttonPanel.add(editBtn.getButton());
		buttonPanel.add(saveBtn.getButton());
		buttonPanel.add(loadBtn.getButton());
		buttonPanel.add(chartBtn.getButton());
	}

	private void setupTablePanel() {
		JPanel recordPanel = new JPanel();
		recordPanel.setLayout(null);
		recordPanel.setBorder(new TitledBorder(new EtchedBorder(), "Records Transactions"));

		helper.attach(this, recordPanel, RECORD_PANEL_RECT);
		recordTable = new JTable(recordTableModel);
		JScrollPane sp = new JScrollPane(recordTable);
		recordPanel.add(sp);
		sp.setBounds(10, 20, (int) (RECORD_PANEL_RECT.width * 0.95), (int) (RECORD_PANEL_RECT.height * 0.92));
	}

	private void setupBalancePanel() {
		JPanel balancePanel = new JPanel();
		balancePanel.setLayout(null);
		balancePanel.setBorder(new TitledBorder(new EtchedBorder(), "Balance"));
		JPanel leftBalancePanel = new JPanel(new GridLayout(2, 2));
		JPanel rightBalancePanel = new JPanel(new GridLayout());

		JLabel incomeLabel = new JLabel("Income");
		JLabel expenseLabel = new JLabel("Expense");
		JLabel balanceLabel = new JLabel("Balance");
		incomeMoney = new JLabel("$0.00");
		expenseMoney = new JLabel("$0.00");
		balanceMoney = new JLabel("$0.00");

		helper.attach(this, balancePanel, BALANCE_PANEL_RECT);
		helper.attach(balancePanel, leftBalancePanel, 20, 20, (int) (PANEL_WIDTH/2) - 20, (int) (BALANCE_PANEL_RECT.height * 0.60));
		helper.attach(balancePanel, rightBalancePanel, (PANEL_WIDTH/2) + 15, 20, (PANEL_WIDTH/2) - 20, (int) (BALANCE_PANEL_RECT.height * 0.60));
		leftBalancePanel.add(incomeLabel);
		leftBalancePanel.add(incomeMoney);
		leftBalancePanel.add(expenseLabel);
		leftBalancePanel.add(expenseMoney);
		rightBalancePanel.add(balanceLabel);
		rightBalancePanel.add(balanceMoney);
	}

	private void setupChartDialog() {
		chartDialog.setSize(500, 500);
		chartDialog.setLocation(this.getX() + 50, this.getY() + 50);
		chartDialog.setVisible(true);
		chartDialog.showDialog();
	}

	private void setupAddDialog() {
		addDialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		addDialog.setLocation(this.getX() + 50, this.getY() + 50);
		addDialog.setVisible(true);
	}

	private void setupEditDialog() {
		int editIndex = recordTable.getSelectedRow();
		if (editIndex != -1) {
			editDialog.initEditInputFields(editIndex);
			editDialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
			editDialog.setLocation(this.getX() + 50, this.getY() + 50);
			editDialog.setVisible(true);
		}
	}

	private void setSubTotals() {
		incomeMoney.setText(balance.getIncome());
		expenseMoney.setText(balance.getExpense());
		balanceMoney.setText(balance.getBalance());
	}

	@Override
	public void update() {
		helper.updateRecordTable(records, recordTableModel);
		setSubTotals();
		System.out.println("Observer got a notification.");
	}
}
