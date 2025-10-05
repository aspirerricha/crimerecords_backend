import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrimeRecordDAO {

    // Insert crime record
    public boolean insertCrimeRecord(CrimeRecord crime) {
        String sql = "INSERT INTO crime_records (case_id, crime_type, location, crime_date, description, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, crime.getCaseId());
            pstmt.setString(2, crime.getCrimeType());
            pstmt.setString(3, crime.getLocation());
            pstmt.setDate(4, new java.sql.Date(crime.getDate().getTime()));
            pstmt.setString(5, crime.getDescription());
            pstmt.setString(6, crime.getStatus());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete crime record
    public boolean deleteCrimeRecord(String caseId) {
        String sql = "DELETE FROM crime_records WHERE case_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, caseId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update crime record status
    public boolean updateCrimeRecord(String caseId, String status) {
        String sql = "UPDATE crime_records SET status = ? WHERE case_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setString(2, caseId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search crime record by case ID
    public CrimeRecord searchCrimeRecord(String caseId) {
        String sql = "SELECT * FROM crime_records WHERE case_id = ?";
        CrimeRecord crime = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, caseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                crime = new CrimeRecord();
                crime.setCaseId(rs.getString("case_id"));
                crime.setCrimeType(rs.getString("crime_type"));
                crime.setLocation(rs.getString("location"));
                crime.setDate(rs.getDate("crime_date"));
                crime.setDescription(rs.getString("description"));
                crime.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crime;
    }

    // Get all crime records
    public List<CrimeRecord> getAllCrimeRecords() {
        String sql = "SELECT * FROM crime_records ORDER BY crime_date DESC";
        List<CrimeRecord> crimes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CrimeRecord crime = new CrimeRecord();
                crime.setCaseId(rs.getString("case_id"));
                crime.setCrimeType(rs.getString("crime_type"));
                crime.setLocation(rs.getString("location"));
                crime.setDate(rs.getDate("crime_date"));
                crime.setDescription(rs.getString("description"));
                crime.setStatus(rs.getString("status"));
                crimes.add(crime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crimes;
    }
}
