package view;

import helper.ComponentAttacher;
import helper.Observer;
import viewModel.AppManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame implements Observer {
	private final String[] TABLE_COLUMNS = {"Date", "Amount", "Description", "Category"};
	private final Rectangle DIALOG_RECT = new Rectangle(this.getX() + 50, this.getY() + 50, 400, 400);
	private final Rectangle CHART_DIALOG_RECT = new Rectangle(this.getX() + 50, this.getY() + 50, 500, 500);
	private final Dimension MAIN_FRAME_DIM = new Dimension(400, 600);
	private final int PANEL_WIDTH = (int) (MAIN_FRAME_DIM.width * 0.97);
	private final Rectangle BUTTON_REC = new Rectangle(80, 30);

	private final Rectangle RECORD_PANEL_RECT = new Rectangle(0, 0, PANEL_WIDTH, (int) (MAIN_FRAME_DIM.height * 0.65));
	private final Rectangle BALANCE_PANEL_RECT = new Rectangle(0, RECORD_PANEL_RECT.height + 5, PANEL_WIDTH, (int) (MAIN_FRAME_DIM.height * 0.15));
	private final Rectangle BUTTON_PANEL_RECT = new Rectangle(0, RECORD_PANEL_RECT.height + BALANCE_PANEL_RECT.height + 10, PANEL_WIDTH, (int) (MAIN_FRAME_DIM.height * 0.15));

	private AppManager appManager;
	private AddDialog addDialog;
	private EditDialog editDialog;
	private DefaultTableModel defaultTable;
	private JTable recordTable;
	private JLabel incomeMoney, expenseMoney, balanceMoney;

	public static void main(String[] args) {
		MainFrame piggyManager = new MainFrame();
		piggyManager.start();
	}

	private void start() {
		appManager = new AppManager();
		addDialog = new AddDialog(appManager);
		editDialog = new EditDialog(appManager);
		appManager.addObserver(this);
		initView();
	}

	private void initView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Piggy Manager");
		setLocation(100, 100);
		setPreferredSize(MAIN_FRAME_DIM);
		setResizable(false);
		setLayout(null);

		ComponentAttacher.attach(this, buttonPanel(), BUTTON_PANEL_RECT);
		ComponentAttacher.attach(this, tablePanel(), RECORD_PANEL_RECT);
		ComponentAttacher.attach(this, balancePanel(), BALANCE_PANEL_RECT);
		initEditDialog();
		initChartDialog();

		pack();
		setVisible(true);
	}

	private JPanel buttonPanel() {
		JPanel returnPanel = new JPanel(new FlowLayout());
		JButton addBtn = new JButton("Add");
		JButton editBtn = new JButton("Edit");
		JButton saveBtn = new JButton("Save");
		JButton loadBtn = new JButton("Load");
		JButton chartBtn = new JButton("Chart");
		ComponentAttacher.attach(returnPanel, addBtn, BUTTON_REC);
		ComponentAttacher.attach(returnPanel, editBtn, BUTTON_REC);
		ComponentAttacher.attach(returnPanel, saveBtn, BUTTON_REC);
		ComponentAttacher.attach(returnPanel, loadBtn, BUTTON_REC);
		ComponentAttacher.attach(returnPanel, chartBtn, BUTTON_REC);

		addBtn.addActionListener(e -> initAddDialog());
		editBtn.addActionListener(e -> initEditDialog());
		saveBtn.addActionListener(e -> appManager.saveRecords());
		loadBtn.addActionListener(e -> appManager.loadRecords());
		chartBtn.addActionListener(e -> appManager.mainFrameClickEvent());
		return returnPanel;
	}

	private JPanel tablePanel() {
		final String TABLE_TITLE = "Records Transactions";
		JPanel returnPanel = new JPanel(null);
		returnPanel.setBorder(new TitledBorder(new EtchedBorder(), TABLE_TITLE));

		defaultTable = new DefaultTableModel(TABLE_COLUMNS, 0);
		recordTable = new JTable(defaultTable);
		JScrollPane sp = new JScrollPane(recordTable);
		ComponentAttacher.attach(returnPanel, sp, 10, 20, (int) (RECORD_PANEL_RECT.width * 0.95), (int)(RECORD_PANEL_RECT.height * 0.92));

		return returnPanel;
	}

	private JPanel balancePanel() {
		final String BALANCE_TITLE = "Balance";
		JPanel returnPanel = new JPanel(null);

		returnPanel.setBorder(new TitledBorder(new EtchedBorder(), BALANCE_TITLE));
		JPanel incomeExpensePrintPanel = new JPanel(new GridLayout(2, 2));
		JPanel balancePrintPanel = new JPanel(new GridLayout());

		JLabel incomeLabel = new JLabel("Income");
		JLabel expenseLabel = new JLabel("Expense");
		JLabel balanceLabel = new JLabel("Balance");
		incomeMoney = new JLabel("$0.00");
		expenseMoney = new JLabel("$0.00");
		balanceMoney = new JLabel("$0.00");

		ComponentAttacher.attach(returnPanel, incomeExpensePrintPanel, 20, 20, (int) (PANEL_WIDTH / 2) - 20, (int) (BALANCE_PANEL_RECT.height * 0.60));
		ComponentAttacher.attach(returnPanel, balancePrintPanel, (PANEL_WIDTH / 2) + 15, 20, (PANEL_WIDTH / 2) - 20, (int) (BALANCE_PANEL_RECT.height * 0.60));
		incomeExpensePrintPanel.add(incomeLabel);
		incomeExpensePrintPanel.add(incomeMoney);
		incomeExpensePrintPanel.add(expenseLabel);
		incomeExpensePrintPanel.add(expenseMoney);
		balancePrintPanel.add(balanceLabel);
		balancePrintPanel.add(balanceMoney);

		return returnPanel;
	}

	private void initAddDialog() {
		addDialog.setBounds(DIALOG_RECT);
		addDialog.setVisible(true);
	}

	private void initEditDialog() {
		int editIndex = recordTable.getSelectedRow();
		if (editIndex != -1) {
			editDialog.initEditInputFields(editIndex);
			editDialog.setBounds(DIALOG_RECT);
			editDialog.setVisible(true);
		}
	}

	private void initChartDialog() {
		ChartDialog chartDialog = new ChartDialog(appManager);
		chartDialog.setBounds(CHART_DIALOG_RECT);
		chartDialog.setVisible(false);
	}

	@Override
	public void update() {
		printTable();
		printMoney();
	}

	private void printMoney() {
		incomeMoney.setText(appManager.getIncome());
		expenseMoney.setText(appManager.getExpense());
		balanceMoney.setText(appManager.getBalance());
	}

	private void printTable() {
		appManager.updateRecordTable(defaultTable);
	}
}
