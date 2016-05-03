

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;


import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;

public class PageRanking {
        public  double AMatrix[][];
        public double BMatrix [][] ;
        public double One_Matrix [][];
        public double VVector[];
        public double Mmatrix [][];
        public double ResultMatrix[];
        public double TempMatrix[];

        public  DataBaseConnect db = new DataBaseConnect();

        double p= 0.15; //damping factor


        /* Initialization of A,B and V
         A = full of zero
         B = full of one * p
         V = 1 / N                              */
        public void initialize(int n){
                AMatrix = new double[n][n];
                BMatrix = new double [n][n];
                One_Matrix = new double [n][n];
                VVector = new double[n];
                Mmatrix = new double [n][n];
                TempMatrix = new double [n];
                ResultMatrix = new double [n];

                int i=0, j=0;

                double init = 1/(double)n; // initial probabilities of all nodes
                System.out.println(" n value:" + n + "\t init value :" + init + "\n");

                // V vector
                System.out.println("V vector");
                for(i=0;i<n;i++){
                        TempMatrix[i]=0;
                        VVector[i]=init;
                        System.out.println(VVector[i]);
                }

                // A and B vector
                for(i=0;i<n;i++){
                        for(j=0;j<n;j++){
                                One_Matrix[i][j]=1;
                                AMatrix[i][j]=0;
                                BMatrix[i][j]= One_Matrix[i][j]*init*p;
                        }
                }

                System.out.println();
                // p * B
                System.out.println("p * B");
                for(j=0;j<n;j++){
                        for(i=0;i<n;i++){
                                System.out.print(BMatrix[i][j]+ " ");
                        }
                        System.out.println();

                }
        }

        /* fill A Vector */
        public void calc(double n)
        {

                try {
                        // select all ids and corresponding outgoing links
                        Statement stmt1 = (Statement) db.conn.createStatement();
                        ResultSet rs1 = stmt1.executeQuery("select id,Outgoing from search2");

                        while (rs1.next())
                        {
                                int currentId = rs1.getInt("id"); // id of current page
                                String outgoings = rs1.getString("Outgoing"); // all outgoing links of current page

                                // if outgoings is null skip the process
                                if(!StringUtils.isNullOrEmpty(outgoings)){
                                        StringTokenizer st = new StringTokenizer(outgoings);
                                        while (st.hasMoreTokens()) {
                                                String each = st.nextToken(); //one outgoing link of current page
                                                //System.out.println(each);

                                                // find the id of <each>
                                                Statement stmt2 = (Statement) db.conn.createStatement();
                                                ResultSet rs2 = stmt2.executeQuery("select id from search2 where link = '"+each+"'");

                                                while (rs2.next())
                                                {
                                                        int id = rs2.getInt("id"); // id of one outgoing page
                                                        //System.out.println(id);
                                                        AMatrix[id-1][currentId-1]=1;  //replace 1 if there is an outgoing edge
                                                }
                                        }
                                }
                        }

                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }


                // update A matrix so that each node can distribute its popularity equally to others
                for (int i = 0; i < AMatrix.length; i++) {
                        int count=0;
                        for (int j = 0; j < AMatrix.length; j++) {
                                if(AMatrix[j][i] != 0)
                                        count++;
                        }
                        if(count != 0){
                                for (int j = 0; j < AMatrix.length; j++) {
                                        AMatrix[j][i] /= count;
                                }
                        }
                }

        }

        /* finds the number of rows means total number of nodes */
        public int getN()
        {
                int n=0;

                try {
                        Statement stmt1 = (Statement) db.conn.createStatement();
                        ResultSet rs1 = stmt1.executeQuery("select count(id) from search2");

                        while (rs1.next())
                        {
                                n = rs1.getInt("count(id)");
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return n;
        }

        /* checks that 2 vectors are equal or not */
        public boolean notEqual(double m1[], double m2[]){
                for (int i = 0; i < m2.length; i++) {
                        //if(m1[i]!=m2[i])
                        if(Math.abs(m1[i]-m2[i]) > 0.0001)
                                return true;
                }

                return false;
        }

        public static void main(String args[]) throws SQLException
        {
                int i=0, j=0;
                PageRanking pr = new PageRanking();
                int n = pr.getN(); // find number of rows
                pr.initialize(n);
                pr.calc(n);

                //  M = (1-p) * A  +  p * B
                for (i = 0; i < n; i++) {
                        for (j = 0; j < n; j++) {
                                pr.AMatrix[i][j] *= (1-pr.p);
                                pr.Mmatrix[i][j] = pr.AMatrix[i][j] + pr.BMatrix[i][j];
                        }
                }

                // print
                System.out.println();
                System.out.println("(1-p) * A");
                for (i = 0; i < n; i++) {
                        for (j = 0; j < n; j++) {
                                System.out.print(String.format( "%.4f", pr.AMatrix[i][j] )+ " ");
                        }
                        System.out.println();
                }

                // print
                System.out.println();
                System.out.println("M = (1-p) * A  +  p * B");
                for (i = 0; i < n; i++) {
                        for (j = 0; j < n; j++) {
                                System.out.print(String.format( "%.4f",pr.Mmatrix[i][j]) + " ");
                        }
                        System.out.println();
                }


                // converge process
                double sum=0;
                int count=0;
                for (i = 0;  i< n; i++) {
                        sum=0;
                        for (j = 0;  j< n; j++) {
                                sum += pr.VVector[j] * pr.Mmatrix[i][j];
                        }
                        pr.ResultMatrix[i] = sum;
                }


                for (j = 0;  j< n; j++) {
                        pr.TempMatrix[j] = pr.ResultMatrix[j];
                }

                while(pr.notEqual(pr.TempMatrix, pr.ResultMatrix) || count==0){
                        count++;
                        for (j = 0;  j< n; j++) {
                                pr.TempMatrix[j] = pr.ResultMatrix[j];
                        }

                        for (i = 0;  i< n; i++) {
                                sum=0;
                                for (j = 0;  j< n; j++) {
                                        sum += pr.ResultMatrix[j] * pr.Mmatrix[i][j];
                                }
                                pr.ResultMatrix[i] = sum;
                                for (j = 0;  j< n; j++) {
                                        System.out.println(pr.ResultMatrix[j]);

                                }
                                System.out.println();

                        }

                }

                //print result
                System.out.println();
                for (j = 0; j < n; j++) {
                        System.out.println(pr.ResultMatrix[j] + " ");
                }
                System.out.println(count);


                // set popularity in table
                Statement stmt1 = (Statement) pr.db.conn.createStatement();
                for (i = 0; i < n; i++) {

                        stmt1.executeUpdate("update search2 set Popularity = "+pr.ResultMatrix[i]+" where id="+(i+1));
                }
        }



}