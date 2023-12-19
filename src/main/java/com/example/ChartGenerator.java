package com.example;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartGenerator {

    public static JFreeChart createBarChart(List<ExcelData> data) {
        // Example: Create a bar chart with team names and the count of interviews
        Map <String, Integer> panelMap = data.stream().filter(data1 -> data1.getMonth().equals("Oct-23") || data1.getMonth().equals("Nov-23")).collect(Collectors.groupingBy(ExcelData::getPanel, Collectors.summingInt(e->1)));

        List<Map.Entry<String,Integer>> top3Panels = panelMap.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue().reversed()).limit(3).collect(Collectors.toList());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        top3Panels.forEach(entry -> dataset.addValue(entry.getValue(), "Interviews", entry.getKey()));

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 3 Panels",
                "Panel",
                "Number of Interviews",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        // Customize chart properties as needed
        chart.setBackgroundPaint(Color.YELLOW);
        return chart;
    }


    public static JFreeChart top3SkillsPieChart(){
        Map<String, Integer> top3Skills = getTop3SkillsFromDatabaseView();
        DefaultPieDataset dataset = new DefaultPieDataset<>();
        top3Skills.forEach(dataset::setValue);


        JFreeChart pieChart = ChartFactory.createPieChart(
                "Top 3 Skills - OCT/NOV 2023",
                dataset,
                true,
                true,
                false
        );
        pieChart.setBackgroundPaint(Color.YELLOW);
        return pieChart;
    }
    private static Map<String,Integer> getTop3SkillsFromDatabaseView(){
        Map<String,Integer> top3Skills = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "Pranav@0906")){
            String sql = "SELECT skill, COUNT(*) as totalInterviews FROM my_view WHERE intMonth IN ('Oct-23','Nov-23') GROUP BY skill ORDER BY totalInterviews DESC LIMIT 3";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()) {
                        String skill = resultSet.getString("skill");
                        int totalInterviews = resultSet.getInt("totalInterviews");
                        top3Skills.put(skill,totalInterviews);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top3Skills;
    }

    public static JFreeChart top3SkillsPeakTimePieChart(){
        Map<String, Integer> top3Skills = getTop3SkillsDuringPeakTime();
        DefaultPieDataset dataset = new DefaultPieDataset<>();
        top3Skills.forEach(dataset::setValue);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Top 3 Skills in Peak time ",
                dataset,
                true,
                true,
                false
        );
        pieChart.setBackgroundPaint(Color.YELLOW);
        return pieChart;
    }




    private static Map<String,Integer> getTop3SkillsDuringPeakTime(){
        Map<String,Integer> top3Skills = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "Pranav@0906")){
            String sql = "SELECT skill, COUNT(*) as totalInterviews FROM my_view2  GROUP BY skill ORDER BY totalInterviews DESC LIMIT 3";

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()) {
                        String skill = resultSet.getString("skill");
                        int totalInterviews = resultSet.getInt("totalInterviews");
                        top3Skills.put(skill,totalInterviews);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return top3Skills;
    }


    public static JFreeChart maxInterviewBarChart()
    {

        Map<String, Integer> map = new HashMap<>();

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "Pranav@0906")) {
            String sql = "SELECT team, COUNT(*) AS totalInterviews from interviewTable WHERE intMonth in ('Oct-23', 'Nov-23') GROUP BY team ORDER BY totalInterviews DESC LIMIT 1";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        String team = resultSet.getString("team");
                        int totalInterviews = resultSet.getInt("totalInterviews");
                        map.put(team, totalInterviews);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DefaultCategoryDataset dataset = createDataset(map);
        JFreeChart barChart = createBarChartMaxInterviews(dataset);
        return barChart;
    }




    private static JFreeChart createBarChartMaxInterviews(DefaultCategoryDataset dataset)
    {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Team with Max interviews - Oct/Nov 2023",
                "Team",
                "Number of Interviews",
                dataset
        );

        barChart.setBackgroundPaint(Color.YELLOW);
        return barChart;
    }


    public static JFreeChart minInterviewBarChart()
    {
        // Map <String,Integer> map = getMinInterviewsByTeam();
        Map<String, Integer> map = new HashMap<>();

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "Pranav@0906")) {
            String sql = "SELECT team, COUNT(*) AS totalInterviews from interviewTable WHERE intMonth in ('Oct-23', 'Nov-23') GROUP BY team ORDER BY totalInterviews LIMIT 1";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        String team = resultSet.getString("team");
                        int totalInterviews = resultSet.getInt("totalInterviews");
                        map.put(team, totalInterviews);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DefaultCategoryDataset dataset = createDataset(map);
        JFreeChart barChart = createBarChartMinInterviews(dataset);
        return barChart;
    }

    private static JFreeChart createBarChartMinInterviews(DefaultCategoryDataset dataset)
    {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Team with Min interviews - Oct/Nov 2023",
                "Team",
                "Number of Interviews",
                dataset
        );

        barChart.setBackgroundPaint(Color.YELLOW);
        return barChart;
    }

    private static DefaultCategoryDataset createDataset(Map<String,Integer> map)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        map.forEach((team, totalInterviews) -> dataset.addValue(totalInterviews, "Interviews", team));
        return dataset;
    }



}