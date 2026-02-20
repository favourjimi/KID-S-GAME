
import java.awt.CardLayout;
//import java.awt.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class GameFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GameFrame.class.getName());
    private String playerName="";
    private int currentQuestion = 0;
    private int score = 0;               // total score across the session
    private int correctCount = 0;        // number of correct answers
    private int wrongCount = 0;          // int timeLeft = 0;  
    private final int TIME_PER_QUESTION = 20;
    private int currentAnswer = 0;
    private long startTime;
    private long endTime;
    private int totalQuestions = 0;       
    private long questionStartNanos = 0;
    private java.util.List<Long> questionTimesMillis = new java.util.ArrayList<>();
    private java.util.List<String> summaryList = new java.util.ArrayList<>();
    private java.util.List<String> questionDetails = new java.util.ArrayList<>();
    // ===== TimeTrial State =====
    private String ttPlayerName = "";
    private int ttTimeLimitSeconds = 0;
    private int ttTimeLeft = 0;
    private javax.swing.Timer ttGameTimer = null;
    private int elapsedMs;
    private int ttCurrentAnswer = 0;
    private int ttQuestionCount = 0; // how many answered (for Q1, Q2,...)
    private long ttQuestionStartNanos = 0;
    // Scoring (simple: +10 per correct)
    private int ttScore = 0;
    private int ttCorrect = 0;
    private int ttWrong = 0;
    
    // Per-question details for summary (expression + correct answer + time)
    private final java.util.List<String> ttQuestionDetails = new java.util.ArrayList<>();
    //for no mistake
    
    private String nmPlayerName = "";
    private int nmScore = 0;
    private int nmCorrectCount = 0;
    private int nmCurrentAnswer = 0;
    private int nmQuestionCount = 0;
    private long nmQuestionStartNanos = 0;
    private final java.util.List<String> nmQuestionDetails = new java.util.ArrayList<>();

    //for take chance

    private int tccorrectCount;
    private int tcwrongCount;
    private String currentPlayerName;
    private List<String> questionHistory = new ArrayList<>();
    private int remainingLives;
    private int correctAnswer;
    private int tcscore;
    private int tctotalQuestions;
    
    //for multipler time trial
    
// At the top of your JFrame/controller class
private int playerIndex = 0;  // default to player 0 (adjust as needed)

// Game state variables
private String[] playerNames = new String[2];
private int[] scores = new int[2];
private int currentPlayerIndex;
private int timeLimitSeconds;
private int mttcorrectAnswer;
private java.util.Timer timer;
private int player1Score = 0;
private int player2Score = 0;


// For summary tracking

// Track completion of each player
private final boolean[] playerFinished = new boolean[]{false, false}; // [P1, P2]
private boolean resultShown = false;   // prevents duplicate summary/winner

private int player1Correct = 0, player1Wrong = 0;
private int player2Correct = 0, player2Wrong = 0;
private java.util.List<String> questionHistoryP1 = new java.util.ArrayList<>();
private java.util.List<String> questionHistoryP2 = new java.util.ArrayList<>();

//for multiplayer make a wish
// Game variables
// Player names
String p1Name = "";
String p2Name = "";

// Settings
int mmwtotalQuestions = 0;     // amount player selected from spinner
int p1QuestionsLeft = 0;
int p2QuestionsLeft = 0;

// Scores
int p1Score = 0;
int p2Score = 0;
int p1Correct = 0;
int p1Wrong = 0;
int p2Correct = 0;
int p2Wrong = 0;

// Current operation chosen by user (+, -, x, ÷)
String currentOperation = "";

// Random numbers currently shown
int currentLeft;
int currentRight;
int leftP2;
int RightP2;

// Whose turn is it? 1 or 2
int currentPlayer = 1;

// Summary text
StringBuilder summaryBuilder = new StringBuilder();

private int questionsPerPlayer = 0;
private javax.swing.JButton btnSubmit;   // submits current answer
private javax.swing.JTabbedPane tabPane; // GAMEPLAY | SUMMARY
private int[] asked = new int[]{0, 0};    // questions asked per player
private int[] correct = new int[]{0, 0};

private String player1Name = "Player 1";
private String player2Name = "Player 2";

private int expectedAnswer = 0;       // answer for current question

private final java.util.Random rng = new java.util.Random();
private final StringBuilder summary = new StringBuilder();

private javax.swing.JTextField txtQuestions;  // number of questions each
private javax.swing.JTextField txtPlayer1;
private javax.swing.JTextField txtPlayer2;
private javax.swing.JTextField txtAnswer;

private javax.swing.JLabel lblQuestion;  // shows "7 + 5 = ?"
private javax.swing.JLabel lblTurn;      // shows "Player 1 (Q 1 of 2)"
private javax.swing.JTextArea txtSummary; // in the SUMMARY tab

enum Op { ADD, SUB, MUL, DIV }

private Op currentOp = Op.ADD; // default

// Optional: also keep a string symbol if you display it on labels
private String mmwoperation = ""; // string used for display only



int p1QuestionsAnswered = 0;
int p2QuestionsAnswered = 0;

int rightNum = 0;




// For summary later
StringBuilder p1Summary = new StringBuilder();
StringBuilder p2Summary = new StringBuilder();

// Game state
int mmwcurrentPlayer = 1; // Player 1 starts

int mmwquestionsAnswered = 0;

// Random numbers
int mmwnum1 = 0;
int mmwnum2 = 0;

// Selected operation: "+", "-", "*", "/"
//String mmwoperation = "";

// Correct answer
int mmwcorrectAnswer = 0;

//for no mistake multi

// ===== Game state =====
private int nmcurrentPlayer = 0;                 // 0 = Player 1, 1 = Player 2
private final int[] nmasked    = new int[]{0, 0};
private final int[] nmcorrect  = new int[]{0, 0};
private final int[] nmmistakes = new int[]{0, 0}; // stop as soon as mistakes[player] == 1

private String nmplayer1Name = "Player 1";
private String nmplayer2Name = "Player 2";

private int nmexpectedAnswer = 0;
private final java.util.Random nmrng = new java.util.Random();

// Log for SUMMARY tab
private final StringBuilder nmsummary = new StringBuilder();

// Optional: operation selection (read from operator labels per panel or set via buttons)
enum nmOp { ADD, SUB, MUL, DIV }
private Op nmcurrentOp = Op.ADD; // If you use op buttons globally; else read from label text per panel

private boolean finalScoresWritten = false;
 
   // public String mtcname;
   // public int mtclives;
    //public int mtcscore;    // correct answers
    //public int mtcasked;    // questions attempted
    //public int mtccorrect;
    //public int mtcwrong;
    
    //for multi- no mistake
    
private String[] mnmplayerNames = new String[2];
private int[] mnmscores = new int[2];
private StringBuilder mnmsummary = new StringBuilder();
private int mnmcurrentPlayerIndex = 0;
private int mnmcorrectAnswer; // current question's correct

//for multi-take chances

private String[] mtcplayerNames = new String[2];
private int[] mtclives = new int[2];
private int[] mtcscores = new int[2];
private int[] mtccorrectCount = new int[2];
private int[] mtcwrongCount = new int[2];
private int mtccurrentPlayerIndex = 0;

private StringBuilder mtcsummary = new StringBuilder();


//for take chance multi
private int mtccorrectAnswer; // at the top of your form class


/**
     * Creates new form GameFrame
     */
    public GameFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        exit = new javax.swing.JButton();
        Parent = new javax.swing.JPanel();
        entry = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        firstpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        selectsingle1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        selectmultiplayer = new javax.swing.JButton();
        Back1 = new javax.swing.JButton();
        spanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tomoveback = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        MakeaWish = new javax.swing.JButton();
        NoMistake = new javax.swing.JButton();
        TakeaChance = new javax.swing.JButton();
        TimeTrial = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jSpinner2 = new javax.swing.JSpinner();
        ttStartButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        nmStartButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        nmNameText = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        tcStartButton = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        tcTextField = new javax.swing.JTextField();
        jSpinner4 = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        makeWishGamePanel = new javax.swing.JPanel();
        wishtitle = new javax.swing.JLabel();
        Addition = new javax.swing.JButton();
        Subtraction = new javax.swing.JButton();
        Multiplicaton = new javax.swing.JButton();
        Division = new javax.swing.JButton();
        tPanel = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        wishSubmitButton = new javax.swing.JButton();
        answerField = new javax.swing.JTextField();
        operationLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        questionLabel = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        questionLabel2 = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        gameLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        PlayernameLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        questionCountLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SummaryTextArea = new javax.swing.JTextArea();
        correctLabel = new javax.swing.JLabel();
        wrongLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        backwish = new javax.swing.JButton();
        timetrialPanel = new javax.swing.JPanel();
        trialtitle = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        ttSubmitButton = new javax.swing.JButton();
        ttLabel = new javax.swing.JLabel();
        ttAnswer = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        ttLabel1 = new javax.swing.JLabel();
        ttoperation = new javax.swing.JLabel();
        ttGenerateButton = new javax.swing.JButton();
        ttHeaderPlayerLabel = new javax.swing.JLabel();
        ttHeaderTimeLabel = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ttSummaryDetailsArea = new javax.swing.JTextArea();
        ttSummaryPlayerLabel = new javax.swing.JLabel();
        ttSummaryScoreLabel = new javax.swing.JLabel();
        ttSummaryCorrectLabel = new javax.swing.JLabel();
        ttSummaryWrongLabel = new javax.swing.JLabel();
        ttSummaryQuestionsLabel = new javax.swing.JLabel();
        ttSummaryDurationLabel = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        ttBACK = new javax.swing.JButton();
        NoMistakePanel = new javax.swing.JPanel();
        nmHeaderplayerLabel = new javax.swing.JLabel();
        nmTabbedPane = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        nmLeftLabel = new javax.swing.JLabel();
        nmOperation = new javax.swing.JLabel();
        nmRightLabel = new javax.swing.JLabel();
        nmAnswerTextField = new javax.swing.JTextField();
        nmSubmitButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        GenerateButton = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        nmSummaryPlayerLabel = new javax.swing.JLabel();
        nmSummaryQuestionLabel = new javax.swing.JLabel();
        nmSummaryScoreLabel = new javax.swing.JLabel();
        nmSummaryCorrectLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        nmSummaryDetailsArea = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        nmBack = new javax.swing.JButton();
        TakeChancePanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel20 = new javax.swing.JPanel();
        tcLeftLabel = new javax.swing.JLabel();
        tcoperation = new javax.swing.JLabel();
        tcRightLabel = new javax.swing.JLabel();
        tcAnswerField = new javax.swing.JTextField();
        tcSubmitButton = new javax.swing.JButton();
        livesLabel = new javax.swing.JLabel();
        playerNameLabel = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        summaryNameLabel = new javax.swing.JLabel();
        summaryQuestionsLabel = new javax.swing.JLabel();
        summaryScoreLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        summaryTextArea = new javax.swing.JTextArea();
        tcBack = new javax.swing.JButton();
        summaryCorrectLabel = new javax.swing.JLabel();
        summaryWrongLabel = new javax.swing.JLabel();
        mpanel = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        mMakeWish = new javax.swing.JButton();
        mTimeTrial = new javax.swing.JButton();
        mttpanel = new javax.swing.JPanel();
        timeSpinner = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Player1Field = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        Player2Field = new javax.swing.JTextField();
        mttStartButton = new javax.swing.JButton();
        mmwpanel = new javax.swing.JPanel();
        mmwStartButton = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        spinnerQuestions = new javax.swing.JSpinner();
        mmwtxtPlayer1 = new javax.swing.JTextField();
        mmwtxtPlayer2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        mnmpanel = new javax.swing.JPanel();
        mnmbtnStartPopup = new javax.swing.JButton();
        nmtxtPlayer1 = new javax.swing.JLabel();
        nmtxtPlayer2 = new javax.swing.JLabel();
        txtP1Popup = new javax.swing.JTextField();
        txtP2Popup = new javax.swing.JTextField();
        mNoMistake = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        mTakeChance = new javax.swing.JButton();
        mtcpanel = new javax.swing.JPanel();
        mtcStartButton = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        mtcSpinner = new javax.swing.JSpinner();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        mtcPlayer1 = new javax.swing.JTextField();
        mtcPlayer2 = new javax.swing.JTextField();
        mmakewishPanel = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel26 = new javax.swing.JPanel();
        lblA = new javax.swing.JLabel();
        lblOp = new javax.swing.JLabel();
        lblB = new javax.swing.JLabel();
        mmwtxtAnswer = new javax.swing.JTextField();
        mmwSubmitButtonP1 = new javax.swing.JButton();
        mmwlblTurn = new javax.swing.JLabel();
        lblCounter = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        mmwLeftLabel2 = new javax.swing.JLabel();
        mmwoperation1 = new javax.swing.JLabel();
        mmwRightLabel2 = new javax.swing.JLabel();
        mmwSubmitButtonP2 = new javax.swing.JButton();
        mmwanswerTextField1 = new javax.swing.JTextField();
        turnlabelP2 = new javax.swing.JLabel();
        counterlabelP2 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        mmwtxtSummary = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        playAdditionButton = new javax.swing.JButton();
        playSubtractionButton = new javax.swing.JButton();
        playMultiiplicationButton = new javax.swing.JButton();
        playDivisionButton = new javax.swing.JButton();
        mmnomistakePanel = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        mnmBACK = new javax.swing.JButton();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        pnlPlayer1 = new javax.swing.JPanel();
        mnmbtnP1Submit = new javax.swing.JButton();
        mnmlblop1 = new javax.swing.JLabel();
        mnmlbl1B = new javax.swing.JLabel();
        mnmlbl1A = new javax.swing.JLabel();
        mnmtxt1Answer = new javax.swing.JTextField();
        pnlPlayer2 = new javax.swing.JPanel();
        nmbtnSubmitP2 = new javax.swing.JButton();
        mnmlbl2A = new javax.swing.JLabel();
        mnmlblop2 = new javax.swing.JLabel();
        mnmlbl2B = new javax.swing.JLabel();
        txtP2Answer = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        nmtxtSummary = new javax.swing.JTextArea();
        mtimetrialPanel = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        mttLeftLabelP1 = new javax.swing.JLabel();
        mttoperationP1 = new javax.swing.JLabel();
        mttRightLabelP1 = new javax.swing.JLabel();
        mttAnswerFieldP1 = new javax.swing.JTextField();
        mttSubmitButton1 = new javax.swing.JButton();
        jLabelTimerP1 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabelTimerP2 = new javax.swing.JLabel();
        mttOperationLabelP2 = new javax.swing.JLabel();
        mttRightLabelP2 = new javax.swing.JLabel();
        mttleftLabelP2 = new javax.swing.JLabel();
        mttSubmitButton2 = new javax.swing.JButton();
        mttAnswerFieldP2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        mttSummaryNameLabel = new javax.swing.JLabel();
        mttSummaryScoreLabel = new javax.swing.JLabel();
        mttSummaryCorrectLabel = new javax.swing.JLabel();
        mttSummaryWrongLabel = new javax.swing.JLabel();
        mtakechancesPanel = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel30 = new javax.swing.JPanel();
        mtcSubmitButton = new javax.swing.JButton();
        mtcLeftLabel1 = new javax.swing.JLabel();
        mtcOperationLabel1 = new javax.swing.JLabel();
        mtcRightLabel1 = new javax.swing.JLabel();
        mtcAnswerField = new javax.swing.JTextField();
        mtcP1l = new javax.swing.JLabel();
        mtcP1n = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        mtcSubmitButton1 = new javax.swing.JButton();
        mtcAnswerField1 = new javax.swing.JTextField();
        mtcLeftLabel2 = new javax.swing.JLabel();
        mtcOperationLabel2 = new javax.swing.JLabel();
        mtcRightLabel2 = new javax.swing.JLabel();
        mtcP2l = new javax.swing.JLabel();
        mtcP2n = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        mtctxtSummary = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        mtcBackbtn = new javax.swing.JButton();
        Goodbyepanel = new javax.swing.JPanel();
        Goodbye = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel9.setText("jLabel9");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        exit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(this::exitActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(exit)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(exit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Parent.setBackground(new java.awt.Color(255, 255, 255));
        Parent.setLayout(new java.awt.CardLayout());

        entry.setPreferredSize(new java.awt.Dimension(1260, 800));

        jLabel6.setFont(new java.awt.Font("Stencil", 1, 48)); // NOI18N
        jLabel6.setText("WELCOME TO KID'S MATH GAME");

        jToggleButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jToggleButton1.setText("PLAY");
        jToggleButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToggleButton1.addActionListener(this::jToggleButton1ActionPerformed);

        javax.swing.GroupLayout entryLayout = new javax.swing.GroupLayout(entry);
        entry.setLayout(entryLayout);
        entryLayout.setHorizontalGroup(
            entryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entryLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1153, Short.MAX_VALUE)
                .addGap(133, 133, 133))
            .addGroup(entryLayout.createSequentialGroup()
                .addGap(391, 391, 391)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        entryLayout.setVerticalGroup(
            entryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entryLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1)
                .addContainerGap(1009, Short.MAX_VALUE))
        );

        Parent.add(entry, "card4");

        firstpanel.setBackground(new java.awt.Color(204, 204, 204));
        firstpanel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Menu");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Single player");

        selectsingle1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        selectsingle1.setText("Pick");
        selectsingle1.addActionListener(this::selectsingle1ActionPerformed);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Multiplayer");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        selectmultiplayer.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        selectmultiplayer.setText("Pick");
        selectmultiplayer.addActionListener(this::selectmultiplayerActionPerformed);

        Back1.setFont(new java.awt.Font("Tempus Sans ITC", 2, 14)); // NOI18N
        Back1.setText("Back");
        Back1.addActionListener(this::Back1ActionPerformed);

        javax.swing.GroupLayout firstpanelLayout = new javax.swing.GroupLayout(firstpanel);
        firstpanel.setLayout(firstpanelLayout);
        firstpanelLayout.setHorizontalGroup(
            firstpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstpanelLayout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(selectsingle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(selectmultiplayer)
                .addGap(292, 292, 292))
            .addGroup(firstpanelLayout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 605, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256))
            .addGroup(firstpanelLayout.createSequentialGroup()
                .addGroup(firstpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(firstpanelLayout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addComponent(Back1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(firstpanelLayout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        firstpanelLayout.setVerticalGroup(
            firstpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstpanelLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(firstpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(firstpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectsingle1)
                    .addComponent(selectmultiplayer))
                .addGap(100, 100, 100)
                .addComponent(Back1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1048, Short.MAX_VALUE))
        );

        Parent.add(firstpanel, "card2");

        spanel.setBackground(java.awt.SystemColor.activeCaptionText);

        jLabel5.setFont(new java.awt.Font("Sylfaen", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SINGLE PLAYER MODE");

        jLabel7.setText("jLabel7");

        jButton1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton1.setText("BACK");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout tomovebackLayout = new javax.swing.GroupLayout(tomoveback);
        tomoveback.setLayout(tomovebackLayout);
        tomovebackLayout.setHorizontalGroup(
            tomovebackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tomovebackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tomovebackLayout.setVerticalGroup(
            tomovebackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tomovebackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("MODE");

        jLabel2.setFont(new java.awt.Font("Segoe UI Historic", 2, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Please select a mode:");

        MakeaWish.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MakeaWish.setText("Make a Wish");
        MakeaWish.addActionListener(this::MakeaWishActionPerformed);

        NoMistake.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        NoMistake.setText("NoMistake");
        NoMistake.addActionListener(this::NoMistakeActionPerformed);

        TakeaChance.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TakeaChance.setText("Take a Chances");
        TakeaChance.addActionListener(this::TakeaChanceActionPerformed);

        TimeTrial.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TimeTrial.setText("TimeTrial");
        TimeTrial.addActionListener(this::TimeTrialActionPerformed);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("player name");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Questions");

        startButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        startButton.setText("START");
        startButton.addActionListener(this::startButtonActionPerformed);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jSpinner1))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(startButton)
                .addGap(21, 21, 21))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("NAME");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("TIME(SEC)");

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 3600, 1));

        ttStartButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ttStartButton.setText("START");
        ttStartButton.addActionListener(this::ttStartButtonActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ttStartButton)
                .addGap(17, 17, 17))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ttStartButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        nmStartButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nmStartButton.setText("START");
        nmStartButton.addActionListener(this::nmStartButtonActionPerformed);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("NAME");

        jLabel18.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel18.setText("Will end once you get one wrong");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(nmStartButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nmNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(nmNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(nmStartButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addGap(17, 17, 17))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tcStartButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tcStartButton.setText("START");
        tcStartButton.addActionListener(this::tcStartButtonActionPerformed);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("NAME");

        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("LIVES");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tcStartButton)
                .addGap(66, 66, 66))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(57, 57, 57)
                .addComponent(tcStartButton)
                .addGap(22, 22, 22))
        );

        jLabel11.setText("jLabel11");

        javax.swing.GroupLayout spanelLayout = new javax.swing.GroupLayout(spanel);
        spanel.setLayout(spanelLayout);
        spanelLayout.setHorizontalGroup(
            spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spanelLayout.createSequentialGroup()
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(MakeaWish)
                        .addGap(145, 145, 145)
                        .addComponent(TimeTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(TakeaChance))
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(45, 45, 45)
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NoMistake, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(247, 247, 247))
            .addGroup(spanelLayout.createSequentialGroup()
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel7)
                        .addGap(182, 182, 182)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(459, 459, 459)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spanelLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(515, 515, 515))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, spanelLayout.createSequentialGroup()
                        .addComponent(tomoveback, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100))))
        );
        spanelLayout.setVerticalGroup(
            spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spanelLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11))
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MakeaWish)
                            .addComponent(NoMistake)
                            .addComponent(TimeTrial)
                            .addComponent(TakeaChance))))
                .addGap(50, 50, 50)
                .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(spanelLayout.createSequentialGroup()
                        .addGroup(spanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(136, 136, 136)
                        .addComponent(tomoveback, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(239, Short.MAX_VALUE))
        );

        Parent.add(spanel, "card5");

        makeWishGamePanel.setBackground(new java.awt.Color(204, 204, 204));

        wishtitle.setBackground(new java.awt.Color(51, 51, 51));
        wishtitle.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        wishtitle.setForeground(new java.awt.Color(51, 51, 51));
        wishtitle.setText("MAKE A WISH");

        Addition.setText("Play Addition");
        Addition.addActionListener(this::AdditionActionPerformed);

        Subtraction.setText("Play Subtraction");
        Subtraction.addActionListener(this::SubtractionActionPerformed);

        Multiplicaton.setText("Play Multiplication");
        Multiplicaton.addActionListener(this::MultiplicatonActionPerformed);

        Division.setText("Play Division");
        Division.addActionListener(this::DivisionActionPerformed);

        tPanel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        wishSubmitButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        wishSubmitButton.setText("Submit");
        wishSubmitButton.addActionListener(this::wishSubmitButtonActionPerformed);

        answerField.addActionListener(this::answerFieldActionPerformed);

        operationLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        operationLabel.setText("pick an operation");

        questionLabel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        questionLabel2.setBackground(new java.awt.Color(255, 51, 51));
        questionLabel2.setForeground(new java.awt.Color(204, 102, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(questionLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        generateButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        generateButton.setText("click to start generating questions");
        generateButton.addActionListener(this::generateButtonActionPerformed);

        gameLabel.setText("jLabel12");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(wishSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(235, 235, 235))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(answerField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(195, 195, 195))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(gameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(178, 178, 178))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(operationLabel)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(generateButton)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(gameLabel)
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(operationLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(53, 53, 53)
                .addComponent(answerField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(wishSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        tPanel.addTab("GAME PLAY", jPanel3);

        jPanel11.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel11AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        PlayernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PlayernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(PlayernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        timeLabel.setText("jLabel12");

        questionCountLabel.setText("jLabel20");

        SummaryTextArea.setColumns(20);
        SummaryTextArea.setRows(5);
        jScrollPane1.setViewportView(SummaryTextArea);

        correctLabel.setText("jLabel12");

        wrongLabel.setText("jLabel12");

        scoreLabel.setText("jLabel12");

        backwish.setText("BACK");
        backwish.addActionListener(this::backwishActionPerformed);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(questionCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(correctLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(wrongLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(backwish)
                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(backwish)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(questionCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(correctLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(wrongLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tPanel.addTab("Summary", jPanel11);

        javax.swing.GroupLayout makeWishGamePanelLayout = new javax.swing.GroupLayout(makeWishGamePanel);
        makeWishGamePanel.setLayout(makeWishGamePanelLayout);
        makeWishGamePanelLayout.setHorizontalGroup(
            makeWishGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeWishGamePanelLayout.createSequentialGroup()
                .addGroup(makeWishGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(makeWishGamePanelLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(makeWishGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Multiplicaton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Subtraction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Addition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Division, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(makeWishGamePanelLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(wishtitle)))
                .addGap(53, 53, 53)
                .addComponent(tPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(442, Short.MAX_VALUE))
        );
        makeWishGamePanelLayout.setVerticalGroup(
            makeWishGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeWishGamePanelLayout.createSequentialGroup()
                .addGroup(makeWishGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(makeWishGamePanelLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(wishtitle)
                        .addGap(45, 45, 45)
                        .addComponent(Addition)
                        .addGap(76, 76, 76)
                        .addComponent(Subtraction)
                        .addGap(69, 69, 69)
                        .addComponent(Multiplicaton)
                        .addGap(83, 83, 83)
                        .addComponent(Division))
                    .addGroup(makeWishGamePanelLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(tPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(878, Short.MAX_VALUE))
        );

        Parent.add(makeWishGamePanel, "card8");

        timetrialPanel.setBackground(new java.awt.Color(204, 204, 204));
        timetrialPanel.setOpaque(false);

        trialtitle.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        trialtitle.setText("Time Trial");

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));

        ttSubmitButton.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        ttSubmitButton.setText("SUBMIT");
        ttSubmitButton.addActionListener(this::ttSubmitButtonActionPerformed);

        ttLabel.setText("jLabel20");

        jLabel22.setBackground(new java.awt.Color(102, 102, 102));
        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N

        ttLabel1.setText("jLabel12");

        ttoperation.setText("operation");

        ttGenerateButton.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        ttGenerateButton.setText("Click to Play");
        ttGenerateButton.addActionListener(this::ttGenerateButtonActionPerformed);

        ttHeaderPlayerLabel.setText("jLabel28");
        ttHeaderPlayerLabel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ttHeaderPlayerLabelAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        ttHeaderTimeLabel.setText("jLabel28");
        ttHeaderTimeLabel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ttHeaderTimeLabelAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(ttLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(ttoperation, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ttLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(ttAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(ttSubmitButton))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(ttGenerateButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(ttHeaderPlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addComponent(ttHeaderTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ttHeaderPlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(ttHeaderTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ttLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ttoperation)
                    .addComponent(ttLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(ttAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(ttSubmitButton)
                .addGap(120, 120, 120)
                .addComponent(ttGenerateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        jTabbedPane1.addTab("GAME~PLAY", jPanel14);

        jPanel15.setBackground(new java.awt.Color(153, 153, 153));

        ttSummaryDetailsArea.setColumns(20);
        ttSummaryDetailsArea.setRows(5);
        jScrollPane2.setViewportView(ttSummaryDetailsArea);

        ttSummaryPlayerLabel.setText("SummaryPlayerLabel");

        ttSummaryScoreLabel.setText("SummaryScoreLabel");

        ttSummaryCorrectLabel.setText("jLabel12");

        ttSummaryWrongLabel.setText("jLabel12");

        ttSummaryQuestionsLabel.setText("jLabel12");

        ttSummaryDurationLabel.setText("jLabel20");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ttSummaryScoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ttSummaryPlayerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(ttSummaryCorrectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ttSummaryWrongLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ttSummaryQuestionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ttSummaryDurationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(90, 90, 90)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(ttSummaryPlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(ttSummaryScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(ttSummaryCorrectLabel)
                        .addGap(66, 66, 66)
                        .addComponent(ttSummaryWrongLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(ttSummaryQuestionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(ttSummaryDurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SUMMARY", jPanel15);

        ttBACK.setText("BACK");
        ttBACK.addActionListener(this::ttBACKActionPerformed);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ttBACK, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ttBACK, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout timetrialPanelLayout = new javax.swing.GroupLayout(timetrialPanel);
        timetrialPanel.setLayout(timetrialPanelLayout);
        timetrialPanelLayout.setHorizontalGroup(
            timetrialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timetrialPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
            .addGroup(timetrialPanelLayout.createSequentialGroup()
                .addGroup(timetrialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timetrialPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(trialtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timetrialPanelLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(699, Short.MAX_VALUE))
        );
        timetrialPanelLayout.setVerticalGroup(
            timetrialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timetrialPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(trialtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(699, Short.MAX_VALUE))
        );

        Parent.add(timetrialPanel, "card8");

        nmHeaderplayerLabel.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        nmHeaderplayerLabel.setText("NO  MISTAKE");
        nmHeaderplayerLabel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                nmHeaderplayerLabelAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        nmTabbedPane.setBackground(new java.awt.Color(153, 255, 153));

        nmLeftLabel.setText("jLabel12");

        nmOperation.setText("jLabel21");

        nmRightLabel.setText("jLabel23");

        nmSubmitButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nmSubmitButton.setText("SUBMIT");
        nmSubmitButton.addActionListener(this::nmSubmitButtonActionPerformed);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel12.setText("NO MISTAKE");

        GenerateButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        GenerateButton.setText("generate");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(131, 131, 131))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(nmLeftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(nmOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(nmRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(nmAnswerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(nmSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(GenerateButton)))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmLeftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nmOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nmRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94)
                .addComponent(nmAnswerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(nmSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(GenerateButton)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        nmTabbedPane.addTab("GAME-PLAY", jPanel18);

        jPanel17.setBackground(new java.awt.Color(204, 255, 204));

        nmSummaryPlayerLabel.setText("jLabel12");

        nmSummaryQuestionLabel.setText("jLabel20");

        nmSummaryScoreLabel.setText("jLabel21");

        nmSummaryCorrectLabel.setText("jLabel23");

        nmSummaryDetailsArea.setColumns(20);
        nmSummaryDetailsArea.setRows(5);
        jScrollPane3.setViewportView(nmSummaryDetailsArea);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nmSummaryPlayerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                            .addComponent(nmSummaryQuestionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nmSummaryCorrectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(nmSummaryScoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(126, 126, 126))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nmSummaryScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nmSummaryPlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmSummaryCorrectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nmSummaryQuestionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        nmTabbedPane.addTab("SUMMARY", jPanel17);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        nmBack.setText("BACK");
        nmBack.addActionListener(this::nmBackActionPerformed);

        javax.swing.GroupLayout NoMistakePanelLayout = new javax.swing.GroupLayout(NoMistakePanel);
        NoMistakePanel.setLayout(NoMistakePanelLayout);
        NoMistakePanelLayout.setHorizontalGroup(
            NoMistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NoMistakePanelLayout.createSequentialGroup()
                .addGap(364, 364, 364)
                .addGroup(NoMistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nmHeaderplayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nmTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NoMistakePanelLayout.createSequentialGroup()
                .addGap(42, 295, Short.MAX_VALUE)
                .addGroup(NoMistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NoMistakePanelLayout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NoMistakePanelLayout.createSequentialGroup()
                        .addComponent(nmBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(962, 962, 962))))
        );
        NoMistakePanelLayout.setVerticalGroup(
            NoMistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NoMistakePanelLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(nmHeaderplayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(nmTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(nmBack, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        Parent.add(NoMistakePanel, "card9");

        TakeChancePanel.setBackground(new java.awt.Color(204, 255, 255));

        jLabel20.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel20.setText("TAKE A CHANCES");

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tcLeftLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tcLeftLabel.setText("jLabel21");

        tcoperation.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tcoperation.setText("jLabel23");

        tcRightLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tcRightLabel.setText("jLabel24");

        tcAnswerField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        tcSubmitButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tcSubmitButton.setText("SUBMIT");
        tcSubmitButton.addActionListener(this::tcSubmitButtonActionPerformed);

        livesLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        livesLabel.setText("jLabel23");

        playerNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        playerNameLabel.setText("jLabel23");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(tcLeftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(tcoperation)
                        .addGap(36, 36, 36)
                        .addComponent(tcRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(tcSubmitButton))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(tcAnswerField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(livesLabel)
                .addGap(21, 21, 21))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(livesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcLeftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tcoperation)
                    .addComponent(tcRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(tcAnswerField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tcSubmitButton)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("GAME PLAY", jPanel20);

        summaryNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        summaryNameLabel.setText("Player name");

        summaryQuestionsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        summaryQuestionsLabel.setText("Total questions");

        summaryScoreLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        summaryScoreLabel.setText("Score");

        summaryTextArea.setColumns(20);
        summaryTextArea.setRows(5);
        jScrollPane4.setViewportView(summaryTextArea);

        tcBack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tcBack.setText("BACK");
        tcBack.addActionListener(this::tcBackActionPerformed);

        summaryCorrectLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        summaryCorrectLabel.setText("correct count");

        summaryWrongLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        summaryWrongLabel.setText("Wrong count");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(summaryNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(summaryScoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tcBack)
                        .addComponent(summaryCorrectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addComponent(summaryWrongLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(summaryQuestionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(summaryNameLabel)
                .addGap(45, 45, 45)
                .addComponent(summaryQuestionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(summaryScoreLabel)
                .addGap(32, 32, 32)
                .addComponent(summaryCorrectLabel)
                .addGap(29, 29, 29)
                .addComponent(summaryWrongLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(tcBack, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jTabbedPane2.addTab("SUMMARY", jPanel19);

        javax.swing.GroupLayout TakeChancePanelLayout = new javax.swing.GroupLayout(TakeChancePanel);
        TakeChancePanel.setLayout(TakeChancePanelLayout);
        TakeChancePanelLayout.setHorizontalGroup(
            TakeChancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TakeChancePanelLayout.createSequentialGroup()
                .addGroup(TakeChancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TakeChancePanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TakeChancePanelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(786, Short.MAX_VALUE))
        );
        TakeChancePanelLayout.setVerticalGroup(
            TakeChancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TakeChancePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1049, Short.MAX_VALUE))
        );

        Parent.add(TakeChancePanel, "card10");

        mpanel.setBackground(new java.awt.Color(153, 153, 153));

        mMakeWish.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        mMakeWish.setText("MAKE A WISH");
        mMakeWish.addActionListener(this::mMakeWishActionPerformed);

        mTimeTrial.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        mTimeTrial.setText("TIME TRIAL");
        mTimeTrial.addActionListener(this::mTimeTrialActionPerformed);

        mttpanel.setBackground(new java.awt.Color(204, 204, 204));

        timeSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 30, 1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Time(Min):");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Player 1");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Player 2");

        mttStartButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mttStartButton.setText("START");
        mttStartButton.addActionListener(this::mttStartButtonActionPerformed);

        javax.swing.GroupLayout mttpanelLayout = new javax.swing.GroupLayout(mttpanel);
        mttpanel.setLayout(mttpanelLayout);
        mttpanelLayout.setHorizontalGroup(
            mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mttpanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
                .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mttpanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Player1Field, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(Player2Field))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mttpanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mttpanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mttStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        mttpanelLayout.setVerticalGroup(
            mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mttpanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Player1Field, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Player2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(mttpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(mttStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        mmwpanel.setBackground(new java.awt.Color(204, 204, 204));

        mmwStartButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mmwStartButton.setText("START");
        mmwStartButton.addActionListener(this::mmwStartButtonActionPerformed);

        jLabel27.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel27.setText("Player 1");

        jLabel28.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel28.setText("Player 2");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("Questions:");

        spinnerQuestions.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));

        mmwtxtPlayer2.addActionListener(this::mmwtxtPlayer2ActionPerformed);

        javax.swing.GroupLayout mmwpanelLayout = new javax.swing.GroupLayout(mmwpanel);
        mmwpanel.setLayout(mmwpanelLayout);
        mmwpanelLayout.setHorizontalGroup(
            mmwpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mmwpanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(mmwpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mmwpanelLayout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(mmwtxtPlayer2))
                    .addGroup(mmwpanelLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mmwpanelLayout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(mmwtxtPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(mmwpanelLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(mmwStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        mmwpanelLayout.setVerticalGroup(
            mmwpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mmwpanelLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addGroup(mmwpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mmwtxtPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(mmwpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mmwtxtPlayer2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(mmwpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(mmwStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel21.setText("MULTIPLAYER");

        mnmpanel.setBackground(new java.awt.Color(255, 255, 255));

        mnmbtnStartPopup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnmbtnStartPopup.setText("START");
        mnmbtnStartPopup.addActionListener(this::mnmbtnStartPopupActionPerformed);

        nmtxtPlayer1.setText("PLAYER 1");

        nmtxtPlayer2.setText("PLAYER 2");

        javax.swing.GroupLayout mnmpanelLayout = new javax.swing.GroupLayout(mnmpanel);
        mnmpanel.setLayout(mnmpanelLayout);
        mnmpanelLayout.setHorizontalGroup(
            mnmpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mnmpanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(mnmpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nmtxtPlayer1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(nmtxtPlayer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(mnmpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtP1Popup, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtP2Popup, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94))
            .addGroup(mnmpanelLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(mnmbtnStartPopup)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mnmpanelLayout.setVerticalGroup(
            mnmpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mnmpanelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(mnmpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmtxtPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtP1Popup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(mnmpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmtxtPlayer2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtP2Popup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addComponent(mnmbtnStartPopup)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        mNoMistake.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        mNoMistake.setText("NO MISTAKE");
        mNoMistake.addActionListener(this::mNoMistakeActionPerformed);

        jLabel23.setFont(new java.awt.Font("Poor Richard", 1, 36)); // NOI18N
        jLabel23.setText("MODE");

        mTakeChance.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        mTakeChance.setText("TAKE A CHANCE");
        mTakeChance.addActionListener(this::mTakeChanceActionPerformed);

        mtcpanel.setBackground(new java.awt.Color(255, 255, 255));

        mtcStartButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mtcStartButton.setText("START");
        mtcStartButton.addActionListener(this::mtcStartButtonActionPerformed);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Player 1");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Player 2");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("lives");

        mtcSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 3, 1));

        javax.swing.GroupLayout mtcpanelLayout = new javax.swing.GroupLayout(mtcpanel);
        mtcpanel.setLayout(mtcpanelLayout);
        mtcpanelLayout.setHorizontalGroup(
            mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mtcpanelLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mtcpanelLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mtcpanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mtcPlayer1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mtcpanelLayout.createSequentialGroup()
                                .addComponent(mtcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(mtcPlayer2))))
                .addGap(89, 89, 89)
                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(99, 99, 99))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mtcpanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mtcStartButton)
                .addGap(150, 150, 150))
        );
        mtcpanelLayout.setVerticalGroup(
            mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mtcpanelLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcPlayer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mtcpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addComponent(mtcStartButton)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(353, 353, 353)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(mMakeWish, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(mmwpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(mTimeTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mttpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                .addComponent(mnmpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(mNoMistake, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                .addComponent(mtcpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(mTakeChance, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel21))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(mnmpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(mNoMistake, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(mtcpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(210, 210, 210)
                                .addComponent(mTakeChance, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(478, 478, 478))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(mmwpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(211, 211, 211)
                                .addComponent(mMakeWish, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mTimeTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mttpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout mpanelLayout = new javax.swing.GroupLayout(mpanel);
        mpanel.setLayout(mpanelLayout);
        mpanelLayout.setHorizontalGroup(
            mpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        mpanelLayout.setVerticalGroup(
            mpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mpanelLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 1338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 559, Short.MAX_VALUE))
        );

        Parent.add(mpanel, "card7");

        jLabel39.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel39.setText("MAKE A WISH MULTIPLAYER MODE");

        jTabbedPane5.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N

        jTabbedPane6.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        lblA.setText("left");

        lblOp.setText("pick an operation");

        lblB.setText("right");

        mmwtxtAnswer.addActionListener(this::mmwtxtAnswerActionPerformed);

        mmwSubmitButtonP1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mmwSubmitButtonP1.setText("SUBMIT");
        mmwSubmitButtonP1.addActionListener(this::mmwSubmitButtonP1ActionPerformed);

        mmwlblTurn.setText("Turn");

        lblCounter.setText("Counter");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(mmwlblTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(lblA, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addComponent(lblOp, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(lblB, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mmwtxtAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(mmwSubmitButtonP1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(335, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(mmwlblTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lblCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblA, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(mmwtxtAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(mmwSubmitButtonP1)
                .addGap(356, 356, 356))
        );

        jTabbedPane6.addTab("PLAYER 1", jPanel26);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        mmwLeftLabel2.setText("Left");

        mmwoperation1.setText("pick an operation");

        mmwRightLabel2.setText("Right");

        mmwSubmitButtonP2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mmwSubmitButtonP2.setText("SUBMIT");
        mmwSubmitButtonP2.addActionListener(this::mmwSubmitButtonP2ActionPerformed);

        mmwanswerTextField1.setText("Enter");

        turnlabelP2.setText("Turn");

        counterlabelP2.setText("Counter");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(turnlabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(counterlabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(mmwLeftLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(mmwoperation1))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(552, 552, 552)
                        .addComponent(mmwRightLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addComponent(mmwanswerTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(361, 361, 361)
                        .addComponent(mmwSubmitButtonP2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(299, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(turnlabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(counterlabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mmwLeftLabel2)
                    .addComponent(mmwoperation1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mmwRightLabel2))
                .addGap(81, 81, 81)
                .addComponent(mmwanswerTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(mmwSubmitButtonP2)
                .addContainerGap(348, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("PLAYER 2", jPanel27);

        jTabbedPane5.addTab("GAMEPLAY", jTabbedPane6);

        jPanel25.setBackground(new java.awt.Color(204, 204, 204));

        mmwtxtSummary.setColumns(20);
        mmwtxtSummary.setRows(5);
        mmwtxtSummary.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                mmwtxtSummaryAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane6.setViewportView(mmwtxtSummary);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(308, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );

        jTabbedPane5.addTab("SUMMARY", jPanel25);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("BACK");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        playAdditionButton.setText("Play Addition");
        playAdditionButton.addActionListener(this::playAdditionButtonActionPerformed);

        playSubtractionButton.setText("Play Subtraction");
        playSubtractionButton.addActionListener(this::playSubtractionButtonActionPerformed);

        playMultiiplicationButton.setText("Play Multiplication");
        playMultiiplicationButton.addActionListener(this::playMultiiplicationButtonActionPerformed);

        playDivisionButton.setText("Play Division");
        playDivisionButton.addActionListener(this::playDivisionButtonActionPerformed);

        javax.swing.GroupLayout mmakewishPanelLayout = new javax.swing.GroupLayout(mmakewishPanel);
        mmakewishPanel.setLayout(mmakewishPanelLayout);
        mmakewishPanelLayout.setHorizontalGroup(
            mmakewishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mmakewishPanelLayout.createSequentialGroup()
                .addGroup(mmakewishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mmakewishPanelLayout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(mmakewishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(playAdditionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playSubtractionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playMultiiplicationButton, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(playDivisionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(70, 70, 70)
                        .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mmakewishPanelLayout.createSequentialGroup()
                        .addGap(523, 523, 523)
                        .addComponent(jLabel39)))
                .addContainerGap(115, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mmakewishPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(80, 80, 80))
        );
        mmakewishPanelLayout.setVerticalGroup(
            mmakewishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mmakewishPanelLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mmakewishPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mmakewishPanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mmakewishPanelLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(playAdditionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(playSubtractionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(playMultiiplicationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(playDivisionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(473, Short.MAX_VALUE))
        );

        Parent.add(mmakewishPanel, "card12");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel40.setText("NO MISTAKE MULTIPLAYER V");

        mnmBACK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnmBACK.setText("BACK");
        mnmBACK.addActionListener(this::mnmBACKActionPerformed);

        jTabbedPane7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jTabbedPane8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        mnmbtnP1Submit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnmbtnP1Submit.setText("SUBMIT");
        mnmbtnP1Submit.addActionListener(this::mnmbtnP1SubmitActionPerformed);

        mnmlblop1.setText("operation");

        mnmlbl1B.setText("Right");

        mnmlbl1A.setText("Left");

        javax.swing.GroupLayout pnlPlayer1Layout = new javax.swing.GroupLayout(pnlPlayer1);
        pnlPlayer1.setLayout(pnlPlayer1Layout);
        pnlPlayer1Layout.setHorizontalGroup(
            pnlPlayer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPlayer1Layout.createSequentialGroup()
                .addGroup(pnlPlayer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPlayer1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(mnmlbl1A, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(124, 124, 124)
                        .addComponent(mnmlblop1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(mnmlbl1B, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPlayer1Layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(mnmtxt1Answer, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPlayer1Layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(mnmbtnP1Submit, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(224, Short.MAX_VALUE))
        );
        pnlPlayer1Layout.setVerticalGroup(
            pnlPlayer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPlayer1Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(pnlPlayer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mnmlblop1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mnmlbl1B)
                    .addComponent(mnmlbl1A, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87)
                .addComponent(mnmtxt1Answer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(mnmbtnP1Submit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("PLAYER 1", pnlPlayer1);

        nmbtnSubmitP2.setText("Submit");
        nmbtnSubmitP2.addActionListener(this::nmbtnSubmitP2ActionPerformed);

        mnmlbl2A.setText("Left");

        mnmlblop2.setText("operation");

        mnmlbl2B.setText("Right");

        txtP2Answer.setText("enter");

        javax.swing.GroupLayout pnlPlayer2Layout = new javax.swing.GroupLayout(pnlPlayer2);
        pnlPlayer2.setLayout(pnlPlayer2Layout);
        pnlPlayer2Layout.setHorizontalGroup(
            pnlPlayer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPlayer2Layout.createSequentialGroup()
                .addGroup(pnlPlayer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPlayer2Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(mnmlbl2A, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(mnmlblop2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(mnmlbl2B, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPlayer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlPlayer2Layout.createSequentialGroup()
                            .addGap(262, 262, 262)
                            .addComponent(txtP2Answer, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPlayer2Layout.createSequentialGroup()
                            .addGap(266, 266, 266)
                            .addComponent(nmbtnSubmitP2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(221, Short.MAX_VALUE))
        );
        pnlPlayer2Layout.setVerticalGroup(
            pnlPlayer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPlayer2Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(pnlPlayer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mnmlblop2)
                    .addComponent(mnmlbl2B)
                    .addComponent(mnmlbl2A))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(txtP2Answer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(nmbtnSubmitP2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
        );

        jTabbedPane8.addTab("PLAYER 2", pnlPlayer2);

        jTabbedPane7.addTab("GAME PLAY", jTabbedPane8);

        nmtxtSummary.setColumns(20);
        nmtxtSummary.setRows(5);
        jScrollPane8.setViewportView(nmtxtSummary);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("SUMMARY", jPanel29);

        javax.swing.GroupLayout mmnomistakePanelLayout = new javax.swing.GroupLayout(mmnomistakePanel);
        mmnomistakePanel.setLayout(mmnomistakePanelLayout);
        mmnomistakePanelLayout.setHorizontalGroup(
            mmnomistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mmnomistakePanelLayout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(390, Short.MAX_VALUE))
            .addGroup(mmnomistakePanelLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mnmBACK)
                .addGap(161, 161, 161))
        );
        mmnomistakePanelLayout.setVerticalGroup(
            mmnomistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mmnomistakePanelLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(mmnomistakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mnmBACK, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(677, Short.MAX_VALUE))
        );

        Parent.add(mmnomistakePanel, "card13");

        jLabel38.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel38.setText("TIME TRIAL MULTIPLAYER");

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("BACK");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane3.setFont(new java.awt.Font("Segoe UI Black", 2, 12)); // NOI18N

        jTabbedPane4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setForeground(new java.awt.Color(153, 153, 153));

        mttLeftLabelP1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        mttLeftLabelP1.setText("jLabel39");

        mttoperationP1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        mttoperationP1.setText("jLabel40");

        mttRightLabelP1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mttRightLabelP1.setText("jLabel41");

        mttSubmitButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        mttSubmitButton1.setText("SUBMIT");
        mttSubmitButton1.addActionListener(this::mttSubmitButton1ActionPerformed);

        jLabelTimerP1.setText("Time left:");
        jLabelTimerP1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabelTimerP1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel45.setText("jLabel45");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(mttLeftLabelP1)
                        .addGap(46, 46, 46)
                        .addComponent(mttoperationP1)
                        .addGap(64, 64, 64)
                        .addComponent(mttRightLabelP1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(mttSubmitButton1))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(mttAnswerFieldP1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(163, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTimerP1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mttoperationP1)
                                    .addComponent(mttRightLabelP1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelTimerP1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(mttLeftLabelP1)
                        .addGap(83, 83, 83)))
                .addComponent(mttAnswerFieldP1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(mttSubmitButton1)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("PLAYER 1", jPanel10);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabelTimerP2.setText("Tme Taken");
        jLabelTimerP2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabelTimerP2AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        mttOperationLabelP2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mttOperationLabelP2.setText("operation");

        mttRightLabelP2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mttRightLabelP2.setText("right");

        mttleftLabelP2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mttleftLabelP2.setText("left");

        mttSubmitButton2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        mttSubmitButton2.setText("SUBMIT");
        mttSubmitButton2.addActionListener(this::mttSubmitButton2ActionPerformed);

        mttAnswerFieldP2.setText("Answer");

        jLabel44.setText("Player 2");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTimerP2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(mttSubmitButton2))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(mttleftLabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(mttOperationLabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(mttRightLabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(mttAnswerFieldP2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTimerP2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(110, 110, 110)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mttOperationLabelP2)
                    .addComponent(mttleftLabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mttRightLabelP2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(mttAnswerFieldP2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(mttSubmitButton2)
                .addGap(85, 85, 85))
        );

        jTabbedPane4.addTab("PLAYER 2", jPanel22);

        jTabbedPane3.addTab("GAMEPLAY", jTabbedPane4);

        jPanel23.setBackground(new java.awt.Color(153, 153, 153));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane5.setViewportView(jTextArea1);

        mttSummaryNameLabel.setText("jLabel39");

        mttSummaryScoreLabel.setText("jLabel39");

        mttSummaryCorrectLabel.setText("jLabel40");

        mttSummaryWrongLabel.setText("jLabel39");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mttSummaryNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mttSummaryScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mttSummaryCorrectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mttSummaryWrongLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(mttSummaryNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(mttSummaryScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(mttSummaryCorrectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(mttSummaryWrongLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("SUMMARY", jPanel23);

        javax.swing.GroupLayout mtimetrialPanelLayout = new javax.swing.GroupLayout(mtimetrialPanel);
        mtimetrialPanel.setLayout(mtimetrialPanelLayout);
        mtimetrialPanelLayout.setHorizontalGroup(
            mtimetrialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mtimetrialPanelLayout.createSequentialGroup()
                .addGroup(mtimetrialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mtimetrialPanelLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mtimetrialPanelLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mtimetrialPanelLayout.createSequentialGroup()
                        .addGap(899, 899, 899)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(334, Short.MAX_VALUE))
        );
        mtimetrialPanelLayout.setVerticalGroup(
            mtimetrialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mtimetrialPanelLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel38)
                .addGap(73, 73, 73)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(644, Short.MAX_VALUE))
        );

        Parent.add(mtimetrialPanel, "card11");

        jTabbedPane9.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N

        jTabbedPane10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        mtcSubmitButton.setText("SUBMIT");
        mtcSubmitButton.addActionListener(this::mtcSubmitButtonActionPerformed);

        mtcLeftLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mtcLeftLabel1.setText("Left");

        mtcOperationLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mtcOperationLabel1.setText("operation");

        mtcRightLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mtcRightLabel1.setText("Right");

        mtcP1l.setText("Lives");

        mtcP1n.setText("name");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(mtcLeftLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(mtcOperationLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(mtcRightLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mtcSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcAnswerField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(mtcP1n, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mtcP1l, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mtcP1l, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcP1n, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mtcOperationLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcRightLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcLeftLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addComponent(mtcAnswerField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(mtcSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        jTabbedPane10.addTab("PLAYER 1", jPanel30);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        mtcSubmitButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mtcSubmitButton1.setText("SUBMIT");
        mtcSubmitButton1.addActionListener(this::mtcSubmitButton1ActionPerformed);

        mtcLeftLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mtcLeftLabel2.setText("left");

        mtcOperationLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mtcOperationLabel2.setText("operation");

        mtcRightLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mtcRightLabel2.setText("right");

        mtcP2l.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mtcP2l.setText("lives2");

        mtcP2n.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mtcP2n.setText("player2");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(mtcSubmitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(mtcLeftLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(mtcOperationLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(mtcRightLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(mtcAnswerField1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(237, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(mtcP2n, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mtcP2l, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mtcP2l, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtcP2n, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mtcOperationLabel2)
                    .addComponent(mtcRightLabel2)
                    .addComponent(mtcLeftLabel2))
                .addGap(118, 118, 118)
                .addComponent(mtcAnswerField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(mtcSubmitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
        );

        jTabbedPane10.addTab("PLAYER 2", jPanel31);

        jTabbedPane9.addTab("GAMEPLAY", jTabbedPane10);

        mtctxtSummary.setColumns(20);
        mtctxtSummary.setRows(5);
        jScrollPane7.setViewportView(mtctxtSummary);

        jLabel41.setText("player1");

        jLabel42.setText("player2");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        jTabbedPane9.addTab("SUMMARY", jPanel32);

        mtcBackbtn.setText("BACK");
        mtcBackbtn.addActionListener(this::mtcBackbtnActionPerformed);

        javax.swing.GroupLayout mtakechancesPanelLayout = new javax.swing.GroupLayout(mtakechancesPanel);
        mtakechancesPanel.setLayout(mtakechancesPanelLayout);
        mtakechancesPanelLayout.setHorizontalGroup(
            mtakechancesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mtakechancesPanelLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(mtcBackbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(198, Short.MAX_VALUE))
        );
        mtakechancesPanelLayout.setVerticalGroup(
            mtakechancesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mtakechancesPanelLayout.createSequentialGroup()
                .addGroup(mtakechancesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mtakechancesPanelLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(mtcBackbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mtakechancesPanelLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(531, Short.MAX_VALUE))
        );

        Parent.add(mtakechancesPanel, "card14");

        Goodbyepanel.setBackground(new java.awt.Color(204, 255, 255));

        Goodbye.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        Goodbye.setForeground(new java.awt.Color(153, 153, 153));
        Goodbye.setText("Goodbye");
        Goodbye.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                GoodbyeComponentAdded(evt);
            }
        });

        javax.swing.GroupLayout GoodbyepanelLayout = new javax.swing.GroupLayout(Goodbyepanel);
        Goodbyepanel.setLayout(GoodbyepanelLayout);
        GoodbyepanelLayout.setHorizontalGroup(
            GoodbyepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GoodbyepanelLayout.createSequentialGroup()
                .addGap(409, 409, 409)
                .addComponent(Goodbye, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(685, Short.MAX_VALUE))
        );
        GoodbyepanelLayout.setVerticalGroup(
            GoodbyepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GoodbyepanelLayout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(Goodbye, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1095, Short.MAX_VALUE))
        );

        Parent.add(Goodbyepanel, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        Parent.removeAll();
        Parent.add(Goodbyepanel);
        Parent.repaint();
        Parent.revalidate();   
        // Delay then exit
        new javax.swing.Timer(2000, e -> System.exit(0)).start();

        // TODO add your handling code here:
    }//GEN-LAST:event_exitActionPerformed

    private void Back1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Back1ActionPerformed
        Parent.removeAll();
        Parent.add(entry);
        Parent.repaint();
        Parent.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_Back1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
       Parent.removeAll();
        Parent.add(firstpanel);
        Parent.repaint();
        Parent.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void GoodbyeComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_GoodbyeComponentAdded
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Goodbye");
        new javax.swing.Timer(2000, e -> System.exit(0)).start();
        // TODO add your handling code here:
    }//GEN-LAST:event_GoodbyeComponentAdded

    private void selectsingle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectsingle1ActionPerformed
        Parent.removeAll();
        Parent.add(spanel);
        jPanel5.setVisible(false);
        jPanel4.setVisible(false);
        jPanel6.setVisible(false);
        jPanel7.setVisible(false);
        Parent.repaint();
        Parent.revalidate(); 
        
        
// TODO add your handling code here:
    }//GEN-LAST:event_selectsingle1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Parent.removeAll();
        Parent.add(firstpanel);
        Parent.repaint();
        Parent.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void MakeaWishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MakeaWishActionPerformed
        // Hide other panels if you have them
        jPanel5.setVisible(false);
        jPanel4.setVisible(true);
        jPanel6.setVisible(false);
        jPanel7.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_MakeaWishActionPerformed

    private void wishSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wishSubmitButtonActionPerformed
       // Guard: if no question is currently active
    if (questionStartNanos == 0) {
        JOptionPane.showMessageDialog(
            tPanel,
            "Please generate or start a question first.",
            "No Active Question",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }
    // Check correctness
    int userAnswer;
    try {
        userAnswer = Integer.parseInt(answerField.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            tPanel,
            "Please enter a valid number.",
            "Invalid Input",
            JOptionPane.WARNING_MESSAGE
        );
        return; // stay on the same question
    }
    int a = Integer.parseInt(questionLabel.getText());
    int b = Integer.parseInt(questionLabel2.getText());
    String opDisplay = operationLabel.getText(); // "+", "-", "×", "÷"
String opLogic = opDisplay.equals("×") ? "*" :
                 opDisplay.equals("÷") ? "/" : opDisplay;
    int currentAnswer = mwcalculateAnswer(a, b, opLogic);
    if (userAnswer == currentAnswer) {
        correctCount++;
        score += 10; // fixed points per correct
        JOptionPane.showMessageDialog(
            tPanel,
            "Correct! +10 pts",
            "Result",
            JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        wrongCount++;
        JOptionPane.showMessageDialog(
            tPanel,
            "Wrong! Correct answer: " + currentAnswer,
            "Result",
            JOptionPane.ERROR_MESSAGE
        );
    }

long elapsedMillis = (System.nanoTime() - questionStartNanos) / 1_000_000L;
    questionTimesMillis.add(elapsedMillis);
    questionStartNanos = 0;

    // Build question detail
    String questionText = questionLabel.getText() + " " + operationLabel.getText() + " " + questionLabel2.getText();
    String detail = "Q" + (currentQuestion + 1) + ": " + questionText + " = " + currentAnswer + " (Time: " + elapsedMillis + " ms)";
    questionDetails.add(detail);

// Advance
    currentQuestion++;
    //  If all questions answered → go to Summary
    if (currentQuestion >= totalQuestions) {
        showSummary();
        tPanel.setSelectedIndex(1); // Switch to Summary tab
        return; // Stop here
    }
    generateButtonActionPerformed(null);
    }
    
private int mwcalculateAnswer(int a, int b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": return a / b; // integer division
        default: throw new IllegalArgumentException("Unknown operator: " + op);
    }


        // TODO add your handling code here:
    }//GEN-LAST:event_wishSubmitButtonActionPerformed

    private void AdditionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdditionActionPerformed
        operationLabel.setText("+");

//jLabel1.setText("+");
        // TODO add your handling code here:
    }//GEN-LAST:event_AdditionActionPerformed

    private void TimeTrialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeTrialActionPerformed
        jPanel5.setVisible(true);
        jPanel4.setVisible(false);
        jPanel6.setVisible(false);
        jPanel7.setVisible(false);
// TODO add your handling code here:
    }//GEN-LAST:event_TimeTrialActionPerformed

    private void TakeaChanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TakeaChanceActionPerformed
jPanel5.setVisible(false);
        jPanel4.setVisible(false);
        jPanel6.setVisible(false);
        jPanel7.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_TakeaChanceActionPerformed

    private void NoMistakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoMistakeActionPerformed
jPanel5.setVisible(false);
        jPanel4.setVisible(false);
        jPanel6.setVisible(true);
        jPanel7.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_NoMistakeActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        
        //String playerName = jTextField1.getText().trim();
        int numQuestions = (Integer) jSpinner1.getValue();
        // 1) Validate name
    playerName = jTextField1.getText().trim();
    if (playerName.isEmpty()) {
        JOptionPane.showMessageDialog(
            tPanel,
            "Please enter your name!",
            "Input Required",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }      
        Parent.removeAll();
        Parent.add(makeWishGamePanel);
        Parent.repaint();
        Parent.revalidate();     
        
        // Get spinner value
    totalQuestions = (Integer) jSpinner1.getValue(); // JSpinner returns Integer
    if (totalQuestions <= 0) {
        JOptionPane.showMessageDialog(tPanel, "Please select at least 1 question.", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }
    // 3) Reset session state
    currentQuestion = 0;
    score = 0;
    correctCount = 0;
    wrongCount = 0;
    questionTimesMillis.clear();
            
    // 4) Switch to GAME PLAY tab and start
    tPanel.setSelectedIndex(0); // 1 = Game Play, 0 = Summary
    //tPanel.setSelectedIndex(1); // Switch to gameplay tab
        
        
        // Show welcome message
        gameLabel.setText("Welcome " + playerName + "! Questions: " + numQuestions);
        // Timer to clear message after 4 seconds
        javax.swing.Timer timer = new javax.swing.Timer(4000, e -> {
        gameLabel.setText(""); // Clear the label
    });
        timer.setRepeats(false); // Run only once
        timer.start();
    // Store question count for game logic
        //selectedQuestionCount = numQuestions;
        // TODO add your handling code here:
    }//GEN-LAST:event_startButtonActionPerformed

    private void selectmultiplayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectmultiplayerActionPerformed
        Parent.removeAll();
        Parent.add(mpanel);
        mttpanel.setVisible(false);
        mmwpanel.setVisible(false);
        mnmpanel.setVisible(false);
        mtcpanel.setVisible(false);
        Parent.repaint();
        Parent.revalidate(); 
        // TODO add your handling code here:
    }//GEN-LAST:event_selectmultiplayerActionPerformed

    private void answerFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answerFieldActionPerformed

    private void SubtractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubtractionActionPerformed
            operationLabel.setText("-");
            // TODO add your handling code here:
    }//GEN-LAST:event_SubtractionActionPerformed

    private void MultiplicatonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultiplicatonActionPerformed
        operationLabel.setText("×");
        // TODO add your handling code here:
    }//GEN-LAST:event_MultiplicatonActionPerformed

    private void DivisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DivisionActionPerformed
        operationLabel.setText("÷");
        // TODO add your handling code here:
    }//GEN-LAST:event_DivisionActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
//        generateNextQuestion();


    //private void generateNextQuestion() {
        if (currentQuestion >= totalQuestions) {
        
        tPanel.setSelectedIndex(0); // Summary tab
        return;
    }
        int num1 = (int)(Math.random() * 100) + 1; // Random number between 1 and 100
        int num2 = (int)(Math.random() * 100) + 1;

        questionLabel.setText(String.valueOf(num1));
        questionLabel2.setText(String.valueOf(num2));
        //for division
    if (operationLabel.getText().equals("÷")) {
    num2 = (int)(Math.random() * 9) + 1;} // Avoid zero
    
    // Determine operation from your UI (e.g., operationLabel text)
    String op = operationLabel.getText().trim(); // "+", "-", "×", "÷", etc.
    // Avoid invalid division (by zero) & set currentAnswer
    switch (op) {
        case "+":
            currentAnswer = num1 + num2;
            break;
        case "-":
            currentAnswer = num1 - num2;
            break;
        case "×":
        case "*":
          currentAnswer = num1 * num2;
            break;
        case "÷":
        case "/":
            // Ensure clean division or pick numbers to make it integer
            if (num2 == 0) num2 = 1; // safeguard
            // If you want integer-only results:
            // make dividend a multiple of divisor
            num1 = (num1 / num2) * num2; // adjusts num1
            questionLabel.setText(String.valueOf(num1));     // update after adjust
            questionLabel2.setText(String.valueOf(num2));
            currentAnswer = num1 / num2; // integer division 
            break;
        default:
            // Fallback to addition if no op selected
            op = "+";
            operationLabel.setText(op);
            currentAnswer = num1 + num2;
            break; 
    }
           // Update header/counter UI
    questionCountLabel.setText("Question " + (currentQuestion + 1) + " of " + totalQuestions); 
    // Prepare input
    answerField.setText("");
    answerField.requestFocusInWindow();
    // Start timing for THIS question
    questionStartNanos = System.nanoTime();
        // TODO add your handling code here:
    }//GEN-LAST:event_generateButtonActionPerformed

    private void jPanel11AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel11AncestorAdded
        showSummary();
    } // Prepare summary when Summary tab is shown
    private void showSummary() {
    // Update summary labels
    PlayernameLabel.setText("Player: " + playerName);
    questionCountLabel.setText("Questions: " + totalQuestions);
    correctLabel.setText("Correct: " + correctCount);
    wrongLabel.setText("Wrong: " + wrongCount);
    scoreLabel.setText("Score: " + score);
            
    long totalTime = 0;
    for (Long ms : questionTimesMillis) {
        totalTime += ms;
    }
    long avgTime = questionTimesMillis.isEmpty() ? 0 : totalTime / questionTimesMillis.size();
    timeLabel.setText("Avg time per question: " + avgTime + " ms");

    // Optional: show detailed breakdown in a text area or list
    StringBuilder details = new StringBuilder();
    //for (int i = 0; i < questionTimesMillis.size(); i++) {
      //  details.append("Q").append(i + 1)
        //       .append(": ").append(questionTimesMillis.get(i)).append(" ms\n");
    //}
        for (String q : questionDetails) {
            details.append(q).append("\n");
    }
    
    SummaryTextArea.setText(details.toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel11AncestorAdded

    private void ttStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttStartButtonActionPerformed
     
       // Switch to GAME-PLAY tab
    //tPanel.setSelectedIndex(0);
        ttPlayerName = jTextField2.getText().trim();
    if (ttPlayerName.isEmpty()) {
        JOptionPane.showMessageDialog(tPanel, "Please enter your name!", "Input Required",
                                      JOptionPane.WARNING_MESSAGE);
        return;
    }
    Parent.removeAll();
      Parent.add(timetrialPanel);
      Parent.repaint();
       Parent.revalidate();
    // Spinner holds seconds; enforce 1..3600 (1 hour)
    ttTimeLimitSeconds = (Integer) jSpinner2.getValue();
    if (ttTimeLimitSeconds < 1 || ttTimeLimitSeconds > 3600) {
        JOptionPane.showMessageDialog(tPanel, "Choose between 1 and 3600 seconds.",
                                      "Invalid Time", JOptionPane.WARNING_MESSAGE);
        return;
    }
// Reset session state
    ttTimeLeft = ttTimeLimitSeconds;
    ttScore = 0;
    ttCorrect = 0;
    ttWrong = 0;
    ttQuestionCount = 0;
    ttQuestionDetails.clear();
    ttQuestionStartNanos = 0;
    


    // Initialize headers
    ttHeaderPlayerLabel.setText("Player: " + ttPlayerName);
    //ttHeaderTimeLabel.setText("Time Left: " + formatSeconds(ttTimeLeft));

    // Disable any manual generate button during an active session
    ttGenerateButton.setEnabled(false);

    // Start timer + first question
    startTimeTrialTimer();
    ttGenerateNextQuestion();
    }
    // Start the countdown timer
    //startTimeTrialTimer();
    private void startTimeTrialTimer() {
    stopTimeTrialTimer(); // safety
    ttGameTimer = new javax.swing.Timer(1000, e -> {
        ttTimeLeft--;
        ttHeaderTimeLabel.setText("Time Left: " + formatSeconds(ttTimeLeft));
        if (ttTimeLeft <= 0) {
            ((javax.swing.Timer)e.getSource()).stop();
            endTimeTrialAndShowSummary();
        }
    });
    ttGameTimer.start();
}
    private void stopTimeTrialTimer() {
    if (ttGameTimer != null && ttGameTimer.isRunning()) ttGameTimer.stop();
    }

private String formatSeconds(int s) {
    int mm = s / 60;
    int ss = s % 60;
    return String.format("%02d:%02d", mm, ss);}
    // Generate the first question
    //ttGenerateNextQuestion();
    
private void ttGenerateNextQuestion() {
    // If time ended, go straight to summary
    if (ttTimeLeft <= 0) {
        endTimeTrialAndShowSummary();
        return;
    }

    int a = (int)(Math.random() * 100) + 1;
    int b = (int)(Math.random() * 100) + 1;
    char[] ops = {'+', '-', '×', '÷'};
    char op = ops[(int)(Math.random() * ops.length)];

    // Compute correct answer (integer division for ÷)
    if (op == '÷') {
        if (b == 0) b = 1;
        a = (a / b) * b;         // make divisible
        ttCurrentAnswer = a / b;
    } else if (op == '+') {
        ttCurrentAnswer = a + b;
    } else if (op == '-') {
        ttCurrentAnswer = a - b;
    } else { // ×
        ttCurrentAnswer = a * b;
    }

    // Update UI AFTER setting answer
    ttLabel.setText(String.valueOf(a));
    ttLabel1.setText(String.valueOf(b));
    ttoperation.setText(String.valueOf(op));
    ttAnswer.setText("");
    ttAnswer.requestFocusInWindow();

    // Start per-question timing
    ttQuestionStartNanos = System.nanoTime();


       // TODO add your handling code here:
    }//GEN-LAST:event_ttStartButtonActionPerformed

    private void ttGenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttGenerateButtonActionPerformed
      // Only allow manual generate if session is NOT active
    if (ttTimeLeft > 0 && ttGameTimer != null && ttGameTimer.isRunning()) {
        JOptionPane.showMessageDialog(
            tPanel,
            "Cannot manually generate during active TimeTrial session.",
            "Action Not Allowed",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // For debugging or practice mode: just generate a question
    ttGenerateNextQuestion();

        // TODO add your handling code here:
    }//GEN-LAST:event_ttGenerateButtonActionPerformed

    private void ttSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttSubmitButtonActionPerformed
    // Ensure a question was generated
    if (ttQuestionStartNanos == 0) {
        JOptionPane.showMessageDialog(tPanel, "Please click Start to begin.",
                                      "No Active Question", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Parse user input
    int userAnswer;
    try {
        userAnswer = Integer.parseInt(ttAnswer.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(tPanel, "Please enter a valid number.",
                                      "Invalid Input", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Check correctness and show ONE dialog
    if (userAnswer == ttCurrentAnswer) {
        ttCorrect++;
        ttScore += 10;
        JOptionPane.showMessageDialog(tPanel, "Correct! +10 pts",
                                      "Result", JOptionPane.INFORMATION_MESSAGE);
    } else {
        ttWrong++;
        JOptionPane.showMessageDialog(tPanel, "Wrong! Correct answer: " + ttCurrentAnswer,
                                      "Result", JOptionPane.ERROR_MESSAGE);
    }

    // Record per-question time
    //long elapsedMs = (System.nanoTime() - ttQuestionStartNanos) / 1_000_000L;
    //ttQuestionStartNanos = 0; // reset

    // Build detail line exactly how you wanted
    String expr = ttLabel.getText() + " " + ttoperation.getText() + " " + ttLabel1.getText();
    ttQuestionCount++;
    String detail = "Q" + ttQuestionCount + ": " + expr + " = " + ttCurrentAnswer
                  + " (Time: " + formatMillis(elapsedMs) + ")";
    ttQuestionDetails.add(detail);

    // Continue if time remains; otherwise end
    if (ttTimeLeft > 0) {
        ttGenerateNextQuestion();
        
    } else {
        endTimeTrialAndShowSummary();
        
    }
}

private String formatMillis(long ms) {
    if (ms < 1000) return ms + " ms";
    double sec = ms / 1000.0;
    return String.format("%.1f s", sec);
}
       
private void endTimeTrialAndShowSummary() {
    // Stop timer
    stopTimeTrialTimer();

    // Re-enable manual generate for debugging after session
    ttGenerateButton.setEnabled(true);
    ttSubmitButton.setEnabled(false); // optional to prevent further submits

    // Update summary labels
    ttSummaryPlayerLabel.setText("Player: " + ttPlayerName);
    ttSummaryScoreLabel.setText("Score: " + ttScore);
    ttSummaryCorrectLabel.setText("Correct: " + ttCorrect);
    ttSummaryWrongLabel.setText("Wrong: " + ttWrong);
    ttSummaryQuestionsLabel.setText("Answered: " + ttQuestionCount);
    ttSummaryDurationLabel.setText("Total Time: " + formatSeconds(ttTimeLimitSeconds));

    // Fill details area (question, answer, time)
    StringBuilder sb = new StringBuilder();
    for (String line : ttQuestionDetails) {
        sb.append(line).append("\n");
    }
    ttSummaryDetailsArea.setText(sb.toString());

    // Switch to Summary tab
    tPanel.setSelectedIndex(1);

        // TODO add your handling code here:
    }//GEN-LAST:event_ttSubmitButtonActionPerformed

    private void ttHeaderTimeLabelAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ttHeaderTimeLabelAncestorAdded
        ttHeaderTimeLabel.setText("Time Left: " + formatSeconds(ttTimeLeft));
        // TODO add your handling code here:
    }//GEN-LAST:event_ttHeaderTimeLabelAncestorAdded

    private void ttBACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttBACKActionPerformed
        Parent.removeAll();
        Parent.add(spanel);
        Parent.repaint();
        Parent.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_ttBACKActionPerformed

    private void backwishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backwishActionPerformed
        Parent.removeAll();
        Parent.add(spanel);
        Parent.repaint();
        Parent.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_backwishActionPerformed

    private void ttHeaderPlayerLabelAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ttHeaderPlayerLabelAncestorAdded
        ttHeaderPlayerLabel.setText("Player: " + ttPlayerName);
// TODO add your handling code here:
    }//GEN-LAST:event_ttHeaderPlayerLabelAncestorAdded

    private void nmStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmStartButtonActionPerformed
        
        nmPlayerName = nmNameText.getText().trim();
    if (nmPlayerName.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter your name!", "Input Required",
                                      JOptionPane.WARNING_MESSAGE);
        return;
    }
        Parent.removeAll();
        Parent.add(NoMistakePanel);
        Parent.repaint();
        Parent.revalidate(); 
    // Reset state
        nmScore = 0;
        nmCorrectCount = 0;
        nmQuestionCount = 0;
        nmQuestionDetails.clear();

    // Switch to Game-Play tab
        nmTabbedPane.setSelectedIndex(0);
    // Generate first question
        nmGenerateNextQuestion();
}
 
private void nmGenerateNextQuestion() {
    int a = (int)(Math.random() * 100) + 1;
    int b = (int)(Math.random() * 100) + 1;
    char[] ops = {'+', '-', '×', '÷'};
    char op = ops[(int)(Math.random() * ops.length)];

    if (op == '÷') {
        if (b == 0) b = 1;
        a = (a / b) * b;
        nmCurrentAnswer = a / b;
    } else if (op == '+') {
        nmCurrentAnswer = a + b;
    } else if (op == '-') {
        nmCurrentAnswer = a - b;
    } else {
        nmCurrentAnswer = a * b;
    }

    nmLeftLabel.setText(String.valueOf(a));
    nmRightLabel.setText(String.valueOf(b));
    nmOperation.setText(String.valueOf(op));
    nmAnswerTextField.setText("");
    nmAnswerTextField.requestFocusInWindow();

    nmQuestionStartNanos = System.nanoTime();


        // TODO add your handling code here:
    }//GEN-LAST:event_nmStartButtonActionPerformed

    private void nmHeaderplayerLabelAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_nmHeaderplayerLabelAncestorAdded
       // Update header
        nmHeaderplayerLabel.setText("Player: " + nmPlayerName);
        // TODO add your handling code here:
    }//GEN-LAST:event_nmHeaderplayerLabelAncestorAdded

    private void nmSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmSubmitButtonActionPerformed
     if (nmQuestionStartNanos == 0) {
        JOptionPane.showMessageDialog(null, "Please start the game first.",
                                      "No Active Question", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int userAnswer;
    try {
        userAnswer = Integer.parseInt(nmAnswerTextField.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Please enter a valid number.",
                                      "Invalid Input", JOptionPane.WARNING_MESSAGE);
        return;
    }

    long elapsedMs = (System.nanoTime() - nmQuestionStartNanos) / 1_000_000L;
    nmQuestionStartNanos = 0;

    String expr = nmLeftLabel.getText() + " " + nmOperation.getText() + " " + nmRightLabel.getText();
    nmQuestionCount++;

    if (userAnswer == nmCurrentAnswer) {
        nmCorrectCount++;
        nmScore += 10;
        JOptionPane.showMessageDialog(null, "Correct! +10 pts", "Result",
                                      JOptionPane.INFORMATION_MESSAGE);

        nmQuestionDetails.add("Q" + nmQuestionCount + ": " + expr + " = " + nmCurrentAnswer
                              + " (Time: " + formatMillis(elapsedMs) + ")");
        nmGenerateNextQuestion(); // Continue
    } else {
        JOptionPane.showMessageDialog(null, "Wrong! Correct answer: " + nmCurrentAnswer,
                                      "Game Over", JOptionPane.ERROR_MESSAGE);

        nmQuestionDetails.add("Q" + nmQuestionCount + ": " + expr + " = " + nmCurrentAnswer
                              + " (Time: " + formatMillis(elapsedMs) + ")");
        nmEndGameAndShowSummary(); // Stop immediately
    }
    }
private void nmEndGameAndShowSummary() {
    nmSummaryPlayerLabel.setText("Player: " + nmPlayerName);
    nmSummaryScoreLabel.setText("Score: " + nmScore);
    nmSummaryCorrectLabel.setText("Correct: " + nmCorrectCount);
    nmSummaryQuestionLabel.setText("Total Questions: " + nmQuestionCount);

    StringBuilder sb = new StringBuilder();
    for (String line : nmQuestionDetails) {
        sb.append(line).append("\n");
    }
    nmSummaryDetailsArea.setText(sb.toString());

    nmTabbedPane.setSelectedIndex(1); // Switch to Summary tab




        // TODO add your handling code here:
    }//GEN-LAST:event_nmSubmitButtonActionPerformed

    private void nmBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmBackActionPerformed
         Parent.removeAll();
        Parent.add(spanel);
        Parent.repaint();
        Parent.revalidate(); 
        // TODO add your handling code here:
    }//GEN-LAST:event_nmBackActionPerformed

    private void tcStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcStartButtonActionPerformed
    currentPlayerName  = tcTextField.getText().trim();
    remainingLives = (int) jSpinner4.getValue();
    tcscore = 0;
    tctotalQuestions = 0;
    
    if (currentPlayerName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter your name!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }  
    Parent.removeAll();
        Parent.add(TakeChancePanel);
        Parent.repaint();
        Parent.revalidate();   
        
    // Update labels in Game Play panel
    playerNameLabel.setText("Player: " + currentPlayerName);
    livesLabel.setText("Lives: " + remainingLives);
    generateNewQuestion();
}

private void generateNewQuestion() {
    Random rand = new Random();
    int num1 = rand.nextInt(100);
    int num2 = rand.nextInt(100);
    String[] operations = {"+", "-", "*"};
    String op = operations[rand.nextInt(operations.length)];

    tcLeftLabel.setText(String.valueOf(num1));
    tcoperation.setText(op);
    tcRightLabel.setText(String.valueOf(num2));

        int correctAnswer = tccalculateAnswer(num1,num2, op);
    tcAnswerField.setText("");
}

private int tccalculateAnswer(int a,int b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        default: return 0;
    
}



        // TODO add your handling code here:
    }//GEN-LAST:event_tcStartButtonActionPerformed

    private void tcSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcSubmitButtonActionPerformed
        // TODO add your handling code here:
    
String input = tcAnswerField.getText().trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an answer!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int userAnswer;
    try {
        userAnswer = Integer.parseInt(input);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String questionText = tcLeftLabel.getText() + " " + tcoperation.getText() + " " + tcRightLabel.getText();
    String feedback;
    
    if (userAnswer == correctAnswer) {
    tcscore += 10;
    tccorrectCount++;
    feedback = questionText + " = " + userAnswer + "  Correct";
} else {
    remainingLives--;
    tcwrongCount++;
    feedback = questionText + " = " + userAnswer + "  Wrong (Correct: " + correctAnswer + ")";
}

    questionHistory.add(feedback);
    tctotalQuestions++;

    
    // Check game over BEFORE generating new question
    if (remainingLives <= 0) {
        JOptionPane.showMessageDialog(this, "Game Over! No lives left.");
        tcSubmitButton.setEnabled(false);

        // Update summary panel
         updateSummaryPanel();
        
        
    // Switch to Summary tab
        jTabbedPane2.setSelectedIndex(1); // Assuming Summary is second tab
        return;
        }
    }
    
private void updateSummaryPanel() {
    summaryNameLabel.setText("Player: " + currentPlayerName);
    summaryScoreLabel.setText("Score: " + tcscore);
    summaryQuestionsLabel.setText("Total Questions: " + tctotalQuestions);
    summaryCorrectLabel.setText("Correct: " + tccorrectCount);
    summaryWrongLabel.setText("Wrong: " + tcwrongCount);

    // Fill TextArea with question history
    StringBuilder sb = new StringBuilder();
    for (String q : questionHistory) {
        sb.append(q).append("\n");
    }
    summaryTextArea.setText(sb.toString());


    generateNewQuestion();
    }//GEN-LAST:event_tcSubmitButtonActionPerformed

    private void tcBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcBackActionPerformed
Parent.removeAll();
        Parent.add(spanel);
        Parent.repaint();
        Parent.revalidate(); 

        // TODO add your handling code here:
    }//GEN-LAST:event_tcBackActionPerformed

    private void mTimeTrialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTimeTrialActionPerformed
        // TODO add your handling code here:
        mttpanel.setVisible(true);
        mtcpanel.setVisible(false);
        mmwpanel.setVisible(false);
        mnmpanel.setVisible(false);
    }//GEN-LAST:event_mTimeTrialActionPerformed

    private void mMakeWishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMakeWishActionPerformed
        // TODO add your handling code here:
        mmwpanel.setVisible(true);
        mtcpanel.setVisible(false);
        mttpanel.setVisible(false);
        mnmpanel.setVisible(false);
    }//GEN-LAST:event_mMakeWishActionPerformed

    private void mNoMistakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNoMistakeActionPerformed
        // TODO add your handling code here:
         mnmpanel.setVisible(true);
        mtcpanel.setVisible(false);
        mmwpanel.setVisible(false);
        mttpanel.setVisible(false);
    }//GEN-LAST:event_mNoMistakeActionPerformed

    private void mTakeChanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTakeChanceActionPerformed
        // TODO add your handling code here:
         mtcpanel.setVisible(true);
        mttpanel.setVisible(false);
        mmwpanel.setVisible(false);
        mnmpanel.setVisible(false);
    }//GEN-LAST:event_mTakeChanceActionPerformed

    private void mttStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mttStartButtonActionPerformed
        // TODO add your handling code here:
        
    String player1 = Player1Field.getText().trim();
    String player2 = Player2Field.getText().trim();
    int timeLimitMinutes = (int) timeSpinner.getValue();

    if (player1.isEmpty() || player2.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both player names!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }
        Parent.removeAll();
      Parent.add(mtimetrialPanel);
      Parent.repaint();
       Parent.revalidate();
    if (timeLimitMinutes <= 0) {
        JOptionPane.showMessageDialog(this, "Please select a valid time!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Store names and time
    playerNames[0] = player1;
    playerNames[1] = player2;
    scores[0] = 0;
    scores[1] = 0;
    currentPlayerIndex = 0;
    timeLimitSeconds = timeLimitMinutes * 60;

    // Switch to gameplay panel (Player 1 tab)
    CardLayout cl = (CardLayout) Parent.getLayout();
    cl.show(Parent, "mttPanel");
    jTabbedPane4.setSelectedIndex(0); // Player 1 tab

    startTimerForPlayer(0);
    generateQuestionForPlayer(0);
}

private void generateQuestionForPlayer(int playerIndex) {
      Random rand = new Random();
    int num1 = rand.nextInt(100);
    int num2 = rand.nextInt(100);
    String[] operations = {"+", "-", "*"};
    String op = operations[rand.nextInt(operations.length)];

    correctAnswer = mttcalculateAnswer(num1,num2, op);
    if (playerIndex == 0) {
    jLabel45.setText(playerNames[0]); // Top-left label
        mttLeftLabelP1.setText(String.valueOf(num1));
        mttoperationP1.setText(op);
        mttRightLabelP1.setText(String.valueOf(num2));
        
        mttAnswerFieldP1.setText("");
    } else { 
    jLabel44.setText(playerNames[1]); // Top-left label
        mttleftLabelP2.setText(String.valueOf(num1));
        mttOperationLabelP2.setText(op);
        mttRightLabelP2.setText(String.valueOf(num2));
        
        mttAnswerFieldP2.setText("");
    }
}
    private int mttcalculateAnswer(int a, int b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": 
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        default: throw new IllegalArgumentException("Unknown op: " + op);
    }
}

private void startTimerForPlayer(int playerIndex) {
    timer = new java.util.Timer();
    timer.scheduleAtFixedRate(new java.util.TimerTask() {
        int timeLeft = timeLimitSeconds;
        @Override
        public void run() {
            SwingUtilities.invokeLater(() -> {
                
                if (playerIndex == 0) {
                    jLabelTimerP1.setText("Time Left: " + timeLeft + "s");
                } else {
                    jLabelTimerP2.setText("Time Left: " + timeLeft + "s");
                }
            });

            if (timeLeft <= 0) {
                timer.cancel();
                SwingUtilities.invokeLater(() -> {
                   if (playerIndex == 0) {
                       int p1Score = 0;
    // Player 1 finished
                    player1Score = p1Score; // <-- SAVE PLAYER 1 SCORE HERE
    mttSubmitButton1.setEnabled(false);
    jTabbedPane4.setSelectedIndex(1); // Move to Player 2 tab

    startTimerForPlayer(1);
    generateQuestionForPlayer(1);

} else {
                       int p2Score = 0;
    // Player 2 finished
    player2Score = p2Score; // <-- SAVE PLAYER 2 SCORE HERE
    mttSubmitButton2.setEnabled(false);

    // --- DETERMINE WINNER ---
    String result;
    if (player1Score > player2Score) {
        result = Player1Field + " wins!\n\n";
    } else if (player2Score > player1Score) {
        result =Player2Field + " wins!\n\n";
    } else {
        result = "It's a tie!\n\n";
    }

    // Display result and summary
    JOptionPane.showMessageDialog(null, result);
    jTabbedPane4.setSelectedIndex(2); // Switch to Summary panel
    showSummary();
}
                });
            }
            timeLeft--;
        }
    }, 0, 1000);


    
        
    }//GEN-LAST:event_mttStartButtonActionPerformed

    private void mttSubmitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mttSubmitButton1ActionPerformed
        // TODO add your handling code here:
        
    String input = mttAnswerFieldP1.getText().trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an answer!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int userAnswer;
    try {
        userAnswer = Integer.parseInt(input);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
 
if (userAnswer == correctAnswer) {
    scores[playerIndex] += 10;
    JOptionPane.showMessageDialog(this, " Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
} else {
    JOptionPane.showMessageDialog(this, " Wrong! Correct answer: " + correctAnswer, "Result", JOptionPane.ERROR_MESSAGE);
}


    // Track question
    String questionText = mttLeftLabelP1.getText() + " " + mttoperationP1.getText() + " " + mttRightLabelP1.getText();
    if (userAnswer == correctAnswer) {
        scores[0] += 10;
        player1Correct++;
        questionHistoryP1.add(questionText + " = " + userAnswer + "  Correct");
    } else {
        player1Wrong++;
        questionHistoryP1.add(questionText + " = " + userAnswer + "  Wrong (Correct: " + correctAnswer + ")");
    }

    generateQuestionForPlayer(0);


    }//GEN-LAST:event_mttSubmitButton1ActionPerformed

    private void mttSubmitButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mttSubmitButton2ActionPerformed
        // TODO add your handling code here:
        
   
    String input = mttAnswerFieldP2.getText().trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an answer!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int userAnswer;
    try {
        userAnswer = Integer.parseInt(input);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String questionText = mttleftLabelP2.getText() + " " + mttOperationLabelP2.getText() + " " + mttRightLabelP2.getText();
   
if (userAnswer == correctAnswer) {
    scores[playerIndex] += 10;
    JOptionPane.showMessageDialog(this, " Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
    questionHistoryP2.add(questionText + " = " + userAnswer + "  Correct");
} else {
    JOptionPane.showMessageDialog(this, " Wrong! Correct answer: " + correctAnswer, "Result", JOptionPane.ERROR_MESSAGE); questionHistoryP2.add(questionText + " = " + userAnswer + "  Wrong (Correct: " + correctAnswer + ")");
    //}
}


    
if (ttTimeLeft > 0) {
       generateQuestionForPlayer(1);
        
    } else {
        mttshowSummary();
}


    }
private void mttshowSummary() {
    String winner;
    if (scores[0] > scores[1]) {
        winner = playerNames[0] + " wins!";
    } else if (scores[1] > scores[0]) {
        winner = playerNames[1] + " wins!";
    } else {
        winner = "It's a tie!";
    }

    JOptionPane.showMessageDialog(this, winner);

    mttSummaryNameLabel.setText("Player 1: " + playerNames[0] + " | Player 2: " + playerNames[1]);
    mttSummaryScoreLabel.setText("Scores → P1: " + scores[0] + ", P2: " + scores[1]);
    mttSummaryCorrectLabel.setText("Correct → P1: " + player1Correct + ", P2: " + player2Correct);
    mttSummaryWrongLabel.setText("Wrong → P1: " + player1Wrong + ", P2: " + player2Wrong);

    StringBuilder sb = new StringBuilder();
    sb.append("Player 1 Questions:\n");
    for (String q : questionHistoryP1) sb.append(q).append("\n");
    sb.append("\nPlayer 2 Questions:\n");
    for (String q : questionHistoryP2) sb.append(q).append("\n");

    jTextArea1.setText(sb.toString());

    jTabbedPane3.setSelectedIndex(1); // Switch to Summary tab



    }//GEN-LAST:event_mttSubmitButton2ActionPerformed

    private void jLabelTimerP1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabelTimerP1AncestorAdded
    ttHeaderTimeLabel.setText("Time Left: " + formatSeconds(ttTimeLeft));
            // TODO add your handling code here:
    }//GEN-LAST:event_jLabelTimerP1AncestorAdded

    private void jLabelTimerP2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabelTimerP2AncestorAdded
    ttHeaderTimeLabel.setText("Time Left: " + formatSeconds(ttTimeLeft));
            // TODO add your handling code here:
    }//GEN-LAST:event_jLabelTimerP2AncestorAdded

    private void mmwStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmwStartButtonActionPerformed
    // Get names
    p1Name = mmwtxtPlayer1.getText().trim();
    p2Name = mmwtxtPlayer2.getText().trim();
    
    if (p1Name.isEmpty() || p2Name.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both player names!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }
    // Get question count from spinner
    totalQuestions = (Integer) spinnerQuestions.getValue();

    // Initialize counters
    p1QuestionsLeft = totalQuestions;
    p2QuestionsLeft = totalQuestions;

    // Reset scores
    p1Score = p2Score = 0;
    p1Correct = p2Correct = 0;
    p1Wrong = p2Wrong = 0;

    // Start with Player 1
    currentPlayer = 1;

    Parent.removeAll();
    Parent.add(mmakewishPanel);
    Parent.repaint();
    Parent.revalidate();

    // Update UI to show Player 1 info
    mmwlblTurn.setText("Turn: " + p1Name);
    lblCounter.setText("Questions left: " + p1QuestionsLeft);

    // generate first question
    generateQuestionForPlayer();
}
private void generateQuestionForPlayer() {

    // generate random numbers
    currentLeft = (int)(Math.random() * 100) ;
    currentRight = (int)(Math.random() * 100) ;

    if (currentPlayer == 1) {
        lblA.setText(String.valueOf(currentLeft));
        lblB.setText(String.valueOf(currentRight));
        lblCounter.setText("Questions left: " + p1QuestionsLeft);
        mmwlblTurn.setText("Turn: " + p1Name);
    } else {
        mmwLeftLabel2.setText(String.valueOf(leftP2));
        mmwRightLabel2.setText(String.valueOf(RightP2));
        counterlabelP2.setText("Questions left: " + p2QuestionsLeft);
        turnlabelP2.setText("Turn: " + p2Name);
    }
}
private void updateOperationLabel() {
    if (currentPlayer == 1) {
        mnmlblop1.setText(currentOperation);
    } else {
        mmwoperation1.setText(currentOperation);
    }

        // TODO add your handling code here:
    

    }//GEN-LAST:event_mmwStartButtonActionPerformed

    private void mmwtxtPlayer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmwtxtPlayer2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mmwtxtPlayer2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Parent.removeAll();
    Parent.add(mpanel);
    Parent.repaint();
    Parent.revalidate();         
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    Parent.removeAll();
    Parent.add(mpanel);
    Parent.repaint();
    Parent.revalidate(); 
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void playAdditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playAdditionButtonActionPerformed
    currentOperation = "+";
    lblOp.setText(currentOperation);  // Player 1 label
    mmwoperation1.setText(currentOperation); // Player 2 label
    updateOperationLabel();


    // TODO add your handling code here:
    }//GEN-LAST:event_playAdditionButtonActionPerformed

    private void playSubtractionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playSubtractionButtonActionPerformed
   currentOperation = "-";
    lblOp.setText(currentOperation);  // Player 1 label
    mmwoperation1.setText(currentOperation); 
    updateOperationLabel();

      // TODO add your handling code here:
    }//GEN-LAST:event_playSubtractionButtonActionPerformed

    private void playMultiiplicationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playMultiiplicationButtonActionPerformed
  currentOperation = "x";
   lblOp.setText(currentOperation);  // Player 1 label
    mmwoperation1.setText(currentOperation); 
    updateOperationLabel();


       // TODO add your handling code here:
    }//GEN-LAST:event_playMultiiplicationButtonActionPerformed

    private void playDivisionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playDivisionButtonActionPerformed
    currentOperation = "/";
     lblOp.setText(currentOperation);  // Player 1 label
    mmwoperation1.setText(currentOperation); 
    updateOperationLabel();


// TODO add your handling code here:
    }//GEN-LAST:event_playDivisionButtonActionPerformed

    private void mmwSubmitButtonP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmwSubmitButtonP1ActionPerformed
    String input = mmwtxtAnswer.getText().trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an answer!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }     
        if (lblOp.equals("")) {
        JOptionPane.showMessageDialog(this, "Select an operation first.");
        return;
    }

    int userAnswer;
    try {
        userAnswer = Integer.parseInt(mmwtxtAnswer.getText());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Enter a valid number.");
        return;
    }

    int correctAnswer = mmwcalculateAnswer();

    if (userAnswer == correctAnswer) {
        p1Correct++;
        p1Score++;
        JOptionPane.showMessageDialog(this, "Correct! Next question.");
        summaryBuilder.append(p1Name + ": " + currentLeft + " " + currentOperation + " " + currentRight +
                              " = " + userAnswer + " ✔\n");
    } else {
        p1Wrong++;
        JOptionPane.showMessageDialog(this, "Incorrect! Correct answer = " + correctAnswer);
        summaryBuilder.append(p1Name + ": " + currentLeft + " " + currentOperation + " " + currentRight +
                              " = " + userAnswer + " ✘ (Ans: " + correctAnswer + ")\n");
    }

    p1QuestionsLeft--;

    if (p1QuestionsLeft == 0) {
        mmwcurrentPlayer = 2;
        jTabbedPane6.setSelectedIndex(1);

        JOptionPane.showMessageDialog(this, "Player 1 finished! Now Player 2");
        generateQuestionForPlayer2();
        //generateQuestionForPlayer();
        return;
    }
 }
private void generateQuestionForPlayer2() {
    Random r = new Random();

        int leftP2 = r.nextInt(100);
        int rightP2 = r.nextInt(100);
    
    mmwLeftLabel2.setText(String.valueOf(leftP2));
    mmwRightLabel2.setText(String.valueOf(rightP2));
    turnlabelP2.setText("Turn: " + p1Name);
    counterlabelP2.setText("Questions left: " + p1QuestionsLeft);

    // Update operation symbol based on currentOperation
    mmwoperation1.setText(currentOperation);



        // TODO add your handling code here:
    }//GEN-LAST:event_mmwSubmitButtonP1ActionPerformed

    private void mmwSubmitButtonP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmwSubmitButtonP2ActionPerformed
    String input = mmwanswerTextField1.getText().trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an answer!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }        
    int userAnswer;
    try {
        userAnswer = Integer.parseInt(mmwanswerTextField1.getText());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Enter a valid number.");
        return;
    }
   if (mmwoperation1.equals("")) {
        JOptionPane.showMessageDialog(this, "Select an operation first.");
        return;
    }

    int correctAnswer = mmwcalculateAnswer2();

    if (userAnswer == correctAnswer) {
        p2Correct++;
        p2Score++;
        JOptionPane.showMessageDialog(this, "Correct! Next question.");
        summaryBuilder.append(p2Name + ": " + leftP2 + " " + currentOperation + " " + RightP2 +
                              " = " + userAnswer + " \n");
    } else {
        p2Wrong++;
        JOptionPane.showMessageDialog(this, "Incorrect! Correct answer = " + correctAnswer);
        summaryBuilder.append(p2Name + ": " + leftP2 + " " + currentOperation + " " + RightP2 +
                              " = " + userAnswer + " (Ans: " + correctAnswer + ")\n");
    }

    p2QuestionsLeft--;

    if (p2QuestionsLeft == 0) {
        // GAME FINISHED → GO TO SUMMARY TAB
        jTabbedPane5.setSelectedIndex(1);
        mmwshowSummary();
        return;
    }
    generateQuestionForPlayer2();
    }
    
    
    private int mmwcalculateAnswer() {
    switch (currentOperation) {
        case "+": return leftP2 + currentRight;
        case "-": return leftP2 - currentRight;
        case "x": return leftP2 * currentRight;
        case "/": return leftP2 / currentRight; // integer division
    }
    return 0;
}
     private int mmwcalculateAnswer2() {
    switch (currentOperation) {
        case "+": return currentLeft + RightP2;
        case "-": return currentLeft - RightP2;
        case "x": return currentLeft * RightP2;
        case "/": return currentLeft / RightP2; // integer division
    }
    return 0;
     }
private void mmwshowSummary() {
    mmwtxtSummary.setText(
        "PLAYER 1: " + p1Name + "\n" +
        "Correct: " + p1Correct + "\n" +
        "Wrong: " + p1Wrong + "\n" +
        "Score: " + p1Score + "\n\n" +

        "PLAYER 2: " + p2Name + "\n" +
        "Correct: " + p2Correct + "\n" +
        "Wrong: " + p2Wrong + "\n" +
        "Score: " + p2Score + "\n\n" +

        "=== FULL QUESTION SUMMARY ===\n" +
        summaryBuilder.toString()
    );



        // TODO add your handling code here:
    }//GEN-LAST:event_mmwSubmitButtonP2ActionPerformed

    private void mmwtxtSummaryAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_mmwtxtSummaryAncestorAdded

    // TODO add your handling code here:
    }//GEN-LAST:event_mmwtxtSummaryAncestorAdded

    private void mnmbtnStartPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnmbtnStartPopupActionPerformed
 String p1 = txtP1Popup.getText().trim();
    String p2 = txtP2Popup.getText().trim();

    if (p1.isEmpty() || p2.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both player names!", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (p1.equalsIgnoreCase(p2)) {
        JOptionPane.showMessageDialog(this, "Player names must be different.", "Input Required", JOptionPane.WARNING_MESSAGE);
        return;
    }

    mnmplayerNames[0] = p1;
    mnmplayerNames[1] = p2;
    mnmscores[0] = mnmscores[1] = 0;
    mnmsummary.setLength(0);

    // Switch to No Mistake panel
     Parent.removeAll();
    Parent.add(mmnomistakePanel);
    Parent.repaint();
    Parent.revalidate();
    
    // Show Player 1 tab
    jTabbedPane8.setSelectedIndex(0);
    mnmcurrentPlayerIndex = 0;
    generateNoMistakeQuestionForPlayer(0);
}
    
private void generateNoMistakeQuestionForPlayer(int playerIndex) {
    Random rand = new Random();
    int num1 = rand.nextInt(100);
    int num2 = rand.nextInt(100);
    String[] ops = {"+", "-", "*", "/"};
    String op = ops[rand.nextInt(ops.length)];

    if (op.equals("/")) {
        num2 = rand.nextInt(9) + 1; // avoid zero
        int k = rand.nextInt(10) + 1;
        num1 = k * num2;
    }

    mnmcorrectAnswer = mnmcalculateAnswer(num1, num2, op);

    if (playerIndex == 0) {
        mnmlbl1A.setText(String.valueOf(num1));
        mnmlbl1B.setText(String.valueOf(num2));
        mnmlblop1.setText(op.equals("/") ? "÷" : op);
        mnmtxt1Answer.setText("");
        mnmtxt1Answer.requestFocusInWindow();
    } else {
        mnmlbl2A.setText(String.valueOf(num1));
        mnmlbl2B.setText(String.valueOf(num2));
        mnmlblop2.setText(op.equals("/") ? "÷" : op);
        txtP2Answer.setText("");
        txtP2Answer.requestFocusInWindow();
    }
}

private int mnmcalculateAnswer(int a, int b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": return a / b;
        default: throw new IllegalArgumentException("Unknown op: " + op);
    }



        // TODO add your handling code here:
    }//GEN-LAST:event_mnmbtnStartPopupActionPerformed

    private void mnmbtnP1SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnmbtnP1SubmitActionPerformed
    String ansText = mnmtxt1Answer.getText().trim();
    if (ansText.isEmpty()) return;

    int userAns;
    try {
        userAns = Integer.parseInt(ansText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Enter a valid number.");
        return;
    }

    boolean correct = (userAns == mnmcorrectAnswer);
    summary.append("Player 1: ")
           .append(mnmlbl1A.getText()).append(" ")
           .append(mnmlblop1.getText()).append(" ")
           .append(mnmlbl1B.getText())
           .append(" -> Your answer: ").append(userAns)
           .append(correct ? " (correct)\n" : " (wrong, expected " + mnmcorrectAnswer + ")\n");

    if (correct) {
        mnmscores[0]++;
        generateNoMistakeQuestionForPlayer(0); // keep going
    } else {
        // Switch to Player 2 tab
        currentPlayerIndex = 1;
        jTabbedPane8.setSelectedIndex(1);
        generateNoMistakeQuestionForPlayer(1);
    }
    
    
        // TODO add your handling code here:
    }//GEN-LAST:event_mnmbtnP1SubmitActionPerformed

    private void mnmBACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnmBACKActionPerformed
       Parent.removeAll();
        Parent.add(mpanel);
        Parent.repaint();
        Parent.revalidate(); // TODO add your handling code here:
    }//GEN-LAST:event_mnmBACKActionPerformed

    private void nmbtnSubmitP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmbtnSubmitP2ActionPerformed
  String ansText = txtP2Answer.getText().trim();
    if (ansText.isEmpty()) return;

    int userAns;
    try {
        userAns = Integer.parseInt(ansText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Enter a valid number.");
        return;
    }

    boolean correct = (userAns == mnmcorrectAnswer);
    summary.append("Player 2: ")
           .append(mnmlbl2A.getText()).append(" ")
           .append(mnmlblop2.getText()).append(" ")
           .append(mnmlbl2B.getText())
           .append(" -> Your answer: ").append(userAns)
           .append(correct ? " (correct)\n" : " (wrong, expected " + mnmcorrectAnswer + ")\n");

    if (correct) {
        mnmscores[1]++;
        generateNoMistakeQuestionForPlayer(1); // keep going
    } else {
        // Game over → show summary
        showNoMistakeSummary();
    }
    }
private void showNoMistakeSummary() {
    summary.append("\n---- Final Scores ----\n")
           .append(mnmplayerNames[0]).append(": ").append(mnmscores[0]).append("\n")
           .append(mnmplayerNames[1]).append(": ").append(mnmscores[1]).append("\n");

    nmtxtSummary.setText(summary.toString()); // your summary text area
    jTabbedPane7.setSelectedIndex(1); // switch to Summary tab


     
        // TODO add your handling code here:
    }//GEN-LAST:event_nmbtnSubmitP2ActionPerformed

    private void mmwtxtAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmwtxtAnswerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mmwtxtAnswerActionPerformed

    private void mtcStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mtcStartButtonActionPerformed
    String p1 = mtcPlayer1.getText().trim();
    String p2 = mtcPlayer2.getText().trim();
    int lifeCount = ((Number) mtcSpinner.getValue()).intValue();

    if (p1.isEmpty() || p2.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter both player names!");
        return;
    }
    if (lifeCount < 1) {
        JOptionPane.showMessageDialog(this, "Lives must be >= 1");
        return;
    }
// Show Take Chance panel
     Parent.removeAll();
    Parent.add(mtakechancesPanel);
    Parent.repaint();
    Parent.revalidate();
    
    mtcplayerNames[0] = p1;
    mtcplayerNames[1] = p2;
    mtclives[0] = mtclives[1] = lifeCount;
    mtcscores[0] = mtcscores[1] = 0;
    mtccorrectCount[0] = mtccorrectCount[1] = 0;
    mtcwrongCount[0] = mtcwrongCount[1] = 0;
    mtcsummary.setLength(0);

    

    // Show Player 1 tab
    jTabbedPane10.setSelectedIndex(0);
    mtcP1n.setText(playerNames[0]);
    mtcP1l.setText("Lives: " + mtclives[0]);
    generateTakeChanceQuestion(0);
}

private void generateTakeChanceQuestion(int playerIndex) {
    Random rand = new Random();
    int num1 = rand.nextInt(100);
    int num2 = rand.nextInt(100);
    String[] ops = {"+", "-", "*", "/"};
    String op = ops[rand.nextInt(ops.length)];

    if (op.equals("/")) {
        num2 = rand.nextInt(9) + 1;
        int k = rand.nextInt(10) + 1;
        num1 = k * num2;
    }

    mtccorrectAnswer = mtccalculateAnswer(num1, num2, op);

    if (playerIndex == 0) {
        mtcLeftLabel1.setText(String.valueOf(num1));
        mtcOperationLabel1.setText(op.equals("/") ? "÷" : op);
        mtcRightLabel1.setText(String.valueOf(num2));
        mtcAnswerField.setText("");
        mtcAnswerField.requestFocusInWindow();
    } else {
        mtcLeftLabel2.setText(String.valueOf(num1));
        mtcOperationLabel2.setText(op.equals("/") ? "÷" : op);
        mtcRightLabel2.setText(String.valueOf(num2));
        mtcAnswerField1.setText("");
        mtcAnswerField1.requestFocusInWindow();
    }
}

private int mtccalculateAnswer(int a, int b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": return a / b;
        default: throw new IllegalArgumentException("Unknown op: " + op);
    }


    
// TODO add your handling code here:
    }//GEN-LAST:event_mtcStartButtonActionPerformed

    private void mtcSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mtcSubmitButtonActionPerformed
     String ansText = mtcAnswerField.getText().trim();
    if (ansText.isEmpty()) return;

    int userAns;
    try {
        userAns = Integer.parseInt(ansText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Enter a valid number.");
        return;
    }

    boolean correct = (userAns == mtccorrectAnswer);
    summary.append("Player 1: ")
           .append(mtcLeftLabel1.getText()).append(" ")
           .append(mtcOperationLabel1.getText()).append(" ")
           .append(mtcRightLabel1.getText())
           .append(" -> ").append(userAns)
           .append(correct ? " (correct)\n" : " (wrong, expected " + mtccorrectAnswer + ")\n");
 if (correct) {
        mtcscores[0]++;
        mtccorrectCount[0]++;
        generateTakeChanceQuestion(0);
    } else {
        mtcwrongCount[0]++;
        mtclives[0]--;
    

    mtcP1l.setText("Lives: " + mtclives[0]);

    if (mtclives[0] <= 0) {
        // Switch to Player 2
        mtccurrentPlayerIndex = 1;
        jTabbedPane10.setSelectedIndex(1);
        mtcP2n.setText(mtcplayerNames[1]);
        mtcP2l.setText("Lives: " + mtclives[1]);
        generateTakeChanceQuestion(1);
    } else {
        generateTakeChanceQuestion(0);
    
    }

 }
        // TODO add your handling code here:
    }//GEN-LAST:event_mtcSubmitButtonActionPerformed

    private void mtcSubmitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mtcSubmitButton1ActionPerformed
 String ansText = mtcAnswerField1.getText().trim();
    if (ansText.isEmpty()) return;

    int userAns;
    try {
        userAns = Integer.parseInt(ansText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Enter a valid number.");
        return;
    }

    boolean correct = (userAns == mtccorrectAnswer);
    summary.append("Player 2: ")
           .append(mtcLeftLabel2.getText()).append(" ")
           .append(mtcOperationLabel2.getText()).append(" ")
           .append(mtcRightLabel2.getText())
           .append(" -> ").append(userAns)
           .append(correct ? " (correct)\n" : " (wrong, expected " + mtccorrectAnswer + ")\n");
if (correct) {
    mtcscores[1]++;
    mtccorrectCount[1]++;
    generateTakeChanceQuestion(1);
    
} else {
    mtcwrongCount[1]++;
    mtclives[1]--;
            }

    mtcP2l.setText("Lives: " + mtclives[1]);

    if (mtclives[1] <= 0) {
        showTakeChanceSummary();
    } else {
        generateTakeChanceQuestion(1);
    }
    }
    
private void showTakeChanceSummary() {
    summary.append("\n---- Final Scores ----\n")
           .append(mtcplayerNames[0]).append(": ").append(mtcscores[0])
           .append(" (Correct: ").append(mtccorrectCount[0])
           .append(", Wrong: ").append(mtcwrongCount[0]).append(")\n")
           .append(mtcplayerNames[1]).append(": ").append(mtcscores[1])
           .append(" (Correct: ").append(mtccorrectCount[1])
           .append(", Wrong: ").append(mtcwrongCount[1]).append(")\n");

    mtctxtSummary.setText(summary.toString()); // Your summary text area
    //CardLayout cl = (CardLayout) Parent.getLayout();
    //cl.show(Parent, "summaryPanel"); // Switch to summary panel
    jTabbedPane9.setSelectedIndex(1); 

        // TODO add your handling code here:
    }//GEN-LAST:event_mtcSubmitButton1ActionPerformed

    private void mtcBackbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mtcBackbtnActionPerformed
 Parent.removeAll();
        Parent.add(mpanel);
        Parent.repaint();
        Parent.revalidate();        // TODO add your handling code here:
    }//GEN-LAST:event_mtcBackbtnActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {new GameFrame().setVisible(true);});
        }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Addition;
    private javax.swing.JButton Back1;
    private javax.swing.JButton Division;
    private javax.swing.JButton GenerateButton;
    private javax.swing.JLabel Goodbye;
    private javax.swing.JPanel Goodbyepanel;
    private javax.swing.JButton MakeaWish;
    private javax.swing.JButton Multiplicaton;
    private javax.swing.JButton NoMistake;
    private javax.swing.JPanel NoMistakePanel;
    private javax.swing.JPanel Parent;
    private javax.swing.JTextField Player1Field;
    private javax.swing.JTextField Player2Field;
    private javax.swing.JLabel PlayernameLabel;
    private javax.swing.JButton Subtraction;
    private javax.swing.JTextArea SummaryTextArea;
    private javax.swing.JPanel TakeChancePanel;
    private javax.swing.JButton TakeaChance;
    private javax.swing.JButton TimeTrial;
    private javax.swing.JTextField answerField;
    private javax.swing.JButton backwish;
    private javax.swing.JLabel correctLabel;
    private javax.swing.JLabel counterlabelP2;
    private javax.swing.JPanel entry;
    private javax.swing.JButton exit;
    private javax.swing.JPanel firstpanel;
    private javax.swing.JLabel gameLabel;
    private javax.swing.JButton generateButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTimerP1;
    private javax.swing.JLabel jLabelTimerP2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblA;
    private javax.swing.JLabel lblB;
    private javax.swing.JLabel lblCounter;
    private javax.swing.JLabel lblOp;
    private javax.swing.JLabel livesLabel;
    private javax.swing.JButton mMakeWish;
    private javax.swing.JButton mNoMistake;
    private javax.swing.JButton mTakeChance;
    private javax.swing.JButton mTimeTrial;
    private javax.swing.JPanel makeWishGamePanel;
    private javax.swing.JPanel mmakewishPanel;
    private javax.swing.JPanel mmnomistakePanel;
    private javax.swing.JLabel mmwLeftLabel2;
    private javax.swing.JLabel mmwRightLabel2;
    private javax.swing.JButton mmwStartButton;
    private javax.swing.JButton mmwSubmitButtonP1;
    private javax.swing.JButton mmwSubmitButtonP2;
    private javax.swing.JTextField mmwanswerTextField1;
    private javax.swing.JLabel mmwlblTurn;
    private javax.swing.JLabel mmwoperation1;
    private javax.swing.JPanel mmwpanel;
    private javax.swing.JTextField mmwtxtAnswer;
    private javax.swing.JTextField mmwtxtPlayer1;
    private javax.swing.JTextField mmwtxtPlayer2;
    private javax.swing.JTextArea mmwtxtSummary;
    private javax.swing.JButton mnmBACK;
    private javax.swing.JButton mnmbtnP1Submit;
    private javax.swing.JButton mnmbtnStartPopup;
    private javax.swing.JLabel mnmlbl1A;
    private javax.swing.JLabel mnmlbl1B;
    private javax.swing.JLabel mnmlbl2A;
    private javax.swing.JLabel mnmlbl2B;
    private javax.swing.JLabel mnmlblop1;
    private javax.swing.JLabel mnmlblop2;
    private javax.swing.JPanel mnmpanel;
    private javax.swing.JTextField mnmtxt1Answer;
    private javax.swing.JPanel mpanel;
    private javax.swing.JPanel mtakechancesPanel;
    private javax.swing.JTextField mtcAnswerField;
    private javax.swing.JTextField mtcAnswerField1;
    private javax.swing.JButton mtcBackbtn;
    private javax.swing.JLabel mtcLeftLabel1;
    private javax.swing.JLabel mtcLeftLabel2;
    private javax.swing.JLabel mtcOperationLabel1;
    private javax.swing.JLabel mtcOperationLabel2;
    private javax.swing.JLabel mtcP1l;
    private javax.swing.JLabel mtcP1n;
    private javax.swing.JLabel mtcP2l;
    private javax.swing.JLabel mtcP2n;
    private javax.swing.JTextField mtcPlayer1;
    private javax.swing.JTextField mtcPlayer2;
    private javax.swing.JLabel mtcRightLabel1;
    private javax.swing.JLabel mtcRightLabel2;
    private javax.swing.JSpinner mtcSpinner;
    private javax.swing.JButton mtcStartButton;
    private javax.swing.JButton mtcSubmitButton;
    private javax.swing.JButton mtcSubmitButton1;
    private javax.swing.JPanel mtcpanel;
    private javax.swing.JTextArea mtctxtSummary;
    private javax.swing.JPanel mtimetrialPanel;
    private javax.swing.JTextField mttAnswerFieldP1;
    private javax.swing.JTextField mttAnswerFieldP2;
    private javax.swing.JLabel mttLeftLabelP1;
    private javax.swing.JLabel mttOperationLabelP2;
    private javax.swing.JLabel mttRightLabelP1;
    private javax.swing.JLabel mttRightLabelP2;
    private javax.swing.JButton mttStartButton;
    private javax.swing.JButton mttSubmitButton1;
    private javax.swing.JButton mttSubmitButton2;
    private javax.swing.JLabel mttSummaryCorrectLabel;
    private javax.swing.JLabel mttSummaryNameLabel;
    private javax.swing.JLabel mttSummaryScoreLabel;
    private javax.swing.JLabel mttSummaryWrongLabel;
    private javax.swing.JLabel mttleftLabelP2;
    private javax.swing.JLabel mttoperationP1;
    private javax.swing.JPanel mttpanel;
    private javax.swing.JTextField nmAnswerTextField;
    private javax.swing.JButton nmBack;
    private javax.swing.JLabel nmHeaderplayerLabel;
    private javax.swing.JLabel nmLeftLabel;
    private javax.swing.JTextField nmNameText;
    private javax.swing.JLabel nmOperation;
    private javax.swing.JLabel nmRightLabel;
    private javax.swing.JButton nmStartButton;
    private javax.swing.JButton nmSubmitButton;
    private javax.swing.JLabel nmSummaryCorrectLabel;
    private javax.swing.JTextArea nmSummaryDetailsArea;
    private javax.swing.JLabel nmSummaryPlayerLabel;
    private javax.swing.JLabel nmSummaryQuestionLabel;
    private javax.swing.JLabel nmSummaryScoreLabel;
    private javax.swing.JTabbedPane nmTabbedPane;
    private javax.swing.JButton nmbtnSubmitP2;
    private javax.swing.JLabel nmtxtPlayer1;
    private javax.swing.JLabel nmtxtPlayer2;
    private javax.swing.JTextArea nmtxtSummary;
    private javax.swing.JLabel operationLabel;
    private javax.swing.JButton playAdditionButton;
    private javax.swing.JButton playDivisionButton;
    private javax.swing.JButton playMultiiplicationButton;
    private javax.swing.JButton playSubtractionButton;
    private javax.swing.JLabel playerNameLabel;
    private javax.swing.JPanel pnlPlayer1;
    private javax.swing.JPanel pnlPlayer2;
    private javax.swing.JLabel questionCountLabel;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JLabel questionLabel2;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton selectmultiplayer;
    private javax.swing.JButton selectsingle1;
    private javax.swing.JPanel spanel;
    private javax.swing.JSpinner spinnerQuestions;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel summaryCorrectLabel;
    private javax.swing.JLabel summaryNameLabel;
    private javax.swing.JLabel summaryQuestionsLabel;
    private javax.swing.JLabel summaryScoreLabel;
    private javax.swing.JTextArea summaryTextArea;
    private javax.swing.JLabel summaryWrongLabel;
    private javax.swing.JTabbedPane tPanel;
    private javax.swing.JTextField tcAnswerField;
    private javax.swing.JButton tcBack;
    private javax.swing.JLabel tcLeftLabel;
    private javax.swing.JLabel tcRightLabel;
    private javax.swing.JButton tcStartButton;
    private javax.swing.JButton tcSubmitButton;
    private javax.swing.JTextField tcTextField;
    private javax.swing.JLabel tcoperation;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JSpinner timeSpinner;
    private javax.swing.JPanel timetrialPanel;
    private javax.swing.JPanel tomoveback;
    private javax.swing.JLabel trialtitle;
    private javax.swing.JTextField ttAnswer;
    private javax.swing.JButton ttBACK;
    private javax.swing.JButton ttGenerateButton;
    private javax.swing.JLabel ttHeaderPlayerLabel;
    private javax.swing.JLabel ttHeaderTimeLabel;
    private javax.swing.JLabel ttLabel;
    private javax.swing.JLabel ttLabel1;
    private javax.swing.JButton ttStartButton;
    private javax.swing.JButton ttSubmitButton;
    private javax.swing.JLabel ttSummaryCorrectLabel;
    private javax.swing.JTextArea ttSummaryDetailsArea;
    private javax.swing.JLabel ttSummaryDurationLabel;
    private javax.swing.JLabel ttSummaryPlayerLabel;
    private javax.swing.JLabel ttSummaryQuestionsLabel;
    private javax.swing.JLabel ttSummaryScoreLabel;
    private javax.swing.JLabel ttSummaryWrongLabel;
    private javax.swing.JLabel ttoperation;
    private javax.swing.JLabel turnlabelP2;
    private javax.swing.JTextField txtP1Popup;
    private javax.swing.JTextField txtP2Answer;
    private javax.swing.JTextField txtP2Popup;
    private javax.swing.JButton wishSubmitButton;
    private javax.swing.JLabel wishtitle;
    private javax.swing.JLabel wrongLabel;
    // End of variables declaration//GEN-END:variables
}