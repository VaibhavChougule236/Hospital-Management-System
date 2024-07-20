package Hospital;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HospitalManagement extends JFrame {
     JFrame frame;
    DefaultTableModel tableModel;
    JTable table;
    JFrame frame1;
    HospitalManagement(){
        setTitle("HospitalManagement");
        JLabel label;
        label=new JLabel("WELCOME TO OUR HOSPITAL");
        label.setBounds(10,20,200,50);
        label.setBackground(Color.orange);
        add(label);
        setLayout(null);
        setSize(800,500);
        setVisible(true);

        JButton b1,b2,b3,b4,b5,b6,b7;
        b1=new JButton("Add Patient.");
        b1.setBounds(20,60,180,50);
        b2=new JButton("View Patients.");
        b2.setBounds(20,120,180,50);
        b3=new JButton(" Check Patient.");
        b3.setBounds(20,180,180,50);
        b4=new JButton(" View Doctors.");
        b4.setBounds(20,240,180,50);
        b5=new JButton(" Check Doctor.");
        b5.setBounds(20,300,180,50);
        b6=new JButton(" Book Appointment.");
        b6.setBounds(20,360,180,50);
        b7=new JButton(" Exist");
        b7.setBounds(20,420,180,50);
        add(b1);add(b2);add(b3);add(b4);add(b5);add(b6);add(b7);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPatients();
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Id=Integer.parseInt(JOptionPane.showInputDialog(frame,"Enter Patient Id :"));
                String Name=JOptionPane.showInputDialog(frame,"Enter Patient Name :");
                CheckPatient(Id,Name);
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewDoctors();
            }
        });
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Name=JOptionPane.showInputDialog(frame,"Enter Doctor Name :");
                CheckDoctor(Name);
            }
        });
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        b7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exist();
            }
        });
    }
    private String[][] getDataOfPatientsFromDatabase() {
        String[][] data = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospital", "root", "Vbcd@123");

            // Create a statement with scrollable ResultSet
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Execute the query
            rs = statement.executeQuery("SELECT * FROM Patient");

            // Move the cursor to the last row to get the row count
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            // Initialize the data array
            data = new String[rowCount][5]; // We know there are only 2 columns: Roll_No and Name

            // Populate the data array
            int row = 0;
            while (rs.next()) {
                data[row][0] = rs.getString("Id");
                data[row][1] = rs.getString("Name");
                data[row][2] = rs.getString("Age");
                data[row][3] = rs.getString("Gender");
                data[row][4] = rs.getString("Mob_No");
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
//            // Close the resources
//            try {
//                if (rs != null) rs.close();
//                if (statement != null) statement.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
        return data;
    }
    private String[][] getDataOfDoctorsFromDataBases(){
        String[][] Data=null;
        Connection connection=null;
        Statement statement=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            e.getMessage();
        }
        try{
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospital", "root", "Vbcd@123");
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String query="SELECT * FROM Doctors";
            rs= statement.executeQuery(query);
            rs.last();
            int rows=rs.getRow();
            rs.beforeFirst();
            Data=new String[rows][3];
            int row=0;
            while(rs.next()){
                Data[row][0]=rs.getString("Id");
                Data[row][1]=rs.getString("Name");
                Data[row][2]=rs.getString("Specialization");
                row++;
            }
        }catch (SQLException e){
            e.getMessage();
        }
        return Data;
    }
    public void ViewPatients(){
        JFrame j=new JFrame("Data");
        String[] ColumnNames={"Id","Name","Age","Gender","Mob_No"};
        tableModel=new DefaultTableModel(getDataOfPatientsFromDatabase(),ColumnNames);
        table=new JTable(tableModel);
        //JScrollPane scrollPane=new JScrollPane(table);
        j.setSize(500, 300);
        j.add(table);
        //j.add(scrollPane,BorderLayout.CENTER);
        //j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLocationRelativeTo(null);
        j.setVisible(true);
        //this.dispose();
    }
    public void ViewDoctors(){
        JFrame j=new JFrame("Data");
        String[] ColumnNames={"Id","Name","Specialization"};
        tableModel=new DefaultTableModel(getDataOfDoctorsFromDataBases(),ColumnNames);
        table=new JTable(tableModel);
        JScrollPane scrollPane=new JScrollPane(table);
        j.setSize(500, 300);
        j.add(scrollPane,BorderLayout.CENTER);
        //j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLocationRelativeTo(null);
        j.setVisible(true);
        //this.dispose();
    }
    public void addPatient(){
        String Name= JOptionPane.showInputDialog(frame,"Enter Name :");
        int Age= Integer.parseInt(JOptionPane.showInputDialog(frame,"Enter Age :"));
        String Gender=JOptionPane.showInputDialog(frame,"Enter Gender (M/F) :");
        String MobileNo=JOptionPane.showInputDialog(frame,"Enter MobileNo : :");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospital", "root", "Vbcd@123");
            String query="INSERT INTO patient(Name,Age,Gender,Mob_No) VALUES(?,?,?,?)";
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,Name);
            statement.setInt(2,Age);
            statement.setString(3,Gender);
            statement.setString(4,MobileNo);
            int AffectedRows=statement.executeUpdate();
            if(AffectedRows>0){
                JOptionPane.showMessageDialog(frame, "Patient Added Successfully");
            }else{
                JOptionPane.showMessageDialog(frame, "Fail to add patient");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void CheckPatient(int Id,String Name){
        String query="SELECT * FROM Patient WHERE Id = ? AND Name = ?";
        try{
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospital", "root", "Vbcd@123");
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1,Id);
            statement.setString(2,Name);
            ResultSet Rs=statement.executeQuery();
            if(Rs.next()){
                JOptionPane.showMessageDialog(frame, "The Patient is Present");
            }else {
                JOptionPane.showMessageDialog(frame, "The Patient is not Present");
            }
        }catch (SQLException e){
            e.getMessage();
        }
    }
    public void CheckDoctor(String Name){
        String query="SELECT * FROM doctors WHERE Name=? ";
        try{
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Hospital", "root", "Vbcd@123");
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,Name);
            ResultSet rs=statement.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(frame,"Doctor is Present .");
            }else {
                JOptionPane.showMessageDialog(frame,"Doctor is not Present .");
            }
        }catch (SQLException e){
            e.getMessage();
        }
    }
    public void Exist(){
        try {
            int choice = JOptionPane.showConfirmDialog(frame, "Do You Want to Exit ? ");
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            } else {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
            //SwingUtilities.invokeLater(() ->
        new  HospitalManagement();
    }
}
