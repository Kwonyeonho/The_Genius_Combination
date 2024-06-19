import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main 클래스는 JFrame을 상속받아 GUI 애플리케이션의 메인 프레임을 생성합니다.
public class Main extends JFrame {
    private MyPanel panel = new MyPanel(); // MyPanel 객체를 생성합니다.

    // Main 클래스의 생성자
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 클릭 시 애플리케이션 종료
        setTitle("더지니어스"); // 창의 제목을 설정
        Container contentPane = getContentPane(); // contentPane 객체를 가져옵니다.
        contentPane.setBackground(new Color(0x343434)); // JFrame의 contentPane 배경색 설정
        setSize(1000, 800); // 창의 크기를 설정
        setLayout(new CardLayout()); // CardLayout을 사용하여 패널 전환

        contentPane.add(panel, "MainPanel"); // MainPanel을 contentPane에 추가

        setVisible(true); // 창을 표시
    }

    // MyPanel 클래스는 JPanel을 상속받아 메인 화면의 내용을 설정합니다.
    class MyPanel extends JPanel {
        private JButton button1; // 버튼 객체를 선언

        // MyPanel 클래스의 생성자
        public MyPanel() {
            setBackground(new Color(0x343434)); // MyPanel의 배경색 설정
            setLayout(null); // null 레이아웃을 사용하여 절대 위치 지정
            button1 = new JButton("결!합!"); // 버튼 객체를 생성

            // 버튼 글씨체 설정
            button1.setFont(new Font("HY견명조", Font.BOLD, 20));

            // 버튼 크기 및 위치 설정
            button1.setBounds(350, 330, 200, 100); // x, y, width, height

            // 버튼 배경색 설정
            button1.setBackground(new Color(0x660000));

            // 버튼 글자색 설정
            button1.setForeground(Color.WHITE);

            // 버튼 테두리 및 포커스 제거
            button1.setBorderPainted(false); // 버튼 테두리 제거
            button1.setFocusPainted(false); // 버튼 포커스 제거

            // 버튼 액션 리스너 추가
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Combination 패널로 전환
                    Container contentPane = getContentPane();
                    contentPane.add(new Combination(Main.this), "CombinationPanel"); // Combination 패널을 추가
                    CardLayout cl = (CardLayout) contentPane.getLayout(); // CardLayout 객체를 가져옵니다.
                    cl.show(contentPane, "CombinationPanel"); // CombinationPanel을 표시합니다.
                }
            });

            // 버튼을 패널에 추가
            add(button1);
        }

        // 패널의 구성 요소를 그리는 메서드
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // 부모 클래스의 paintComponent 호출
            g.setColor(new Color(0x660000)); // 텍스트 색상 설정
            g.setFont(new Font("문체부 제목 바탕체", Font.PLAIN, 100)); // 폰트 설정
            g.drawString("더지니어스", 210, 130); // 텍스트를 그립니다.
            g.setColor(new Color(0x660000)); // 텍스트 색상 설정
            g.setFont(new Font("HY견명조", Font.PLAIN, 30)); // 폰트 설정
            g.drawString("Java Final", 380, 180); // 텍스트를 그립니다.
        }
    }

    public static void main(String[] args) {
        new Main(); // Main 객체를 생성하여 게임을 시작
    }

    // MainPanel을 표시하는 메서드
    public void showMainPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout(); // CardLayout 객체를 가져옵니다.
        cl.show(getContentPane(), "MainPanel"); // MainPanel을 표시합니다.
    }
}
