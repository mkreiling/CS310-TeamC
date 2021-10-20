package edu.jsu.mcis.cs310.tas_fa21;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
public class Feature2 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        
        db = new TASDatabase();
        
    }
    
    @Test
    public void testInsertCheckPunch() {
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /* Create New Punch Object */
        Punch p1 = new Punch(101, db.getBadge("021890C0"), 1);
        
        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;
        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        PunchType punchtype = p1.getPunchtype();
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
        /* Retrieve New Punch */
        
        Punch p2 = db.getPunch(punchid);
        /* Compare Punches */
        assertEquals(badgeid, p2.getBadge().getId());
        
        rts = p2.getOriginaltimestamp();
        
        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));
        
    }
    
    @Test
    public void testRetrievePunchList1() {
        
        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 17);
        
        Badge b = db.getBadge("67637925");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts);
        
        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }
        
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();
        
        /* Add Punches */
        
        p2.add(db.getPunch(4716));
        p2.add(db.getPunch(4811));
        p2.add(db.getPunch(4813));
        p2.add(db.getPunch(4847));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals( s2.toString(), s1.toString() );
        
    }
    
    @Test
    public void testRetrievePunchList2() {
        
        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 27);
        
        Badge b = db.getBadge("87176FD7");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts);
        
        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }
        
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();
        
        /* Add Punches */
        
        p2.add(db.getPunch(6089));
        p2.add(db.getPunch(6112));
        p2.add(db.getPunch(6118));
        p2.add(db.getPunch(6129));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals( s2.toString(), s1.toString() );
        
    }
    @Test
    public void testRetrievePunchList3() {
        
        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 5);
        
        Badge b = db.getBadge("95497F63");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts);
        
        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }
        
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();
        
        /* Add Punches */
        
        p2.add(db.getPunch(3463));
        p2.add(db.getPunch(3482));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals( s2.toString(), s1.toString() );
        
    }
    
}