import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.border.LineBorder;

// Combination 클래스 정의, JPanel을 상속받음
public class Combination extends JPanel {
    private MyPanel panel = new MyPanel(); // MyPanel 인스턴스 생성
    private JPanel mainPanel = new JPanel(new BorderLayout()); // 메인 패널 생성, BorderLayout 사용
    private JTextArea textArea = new JTextArea(); // 텍스트 영역 생성
    private int count = 0; // 선택 횟수 카운트
    private int score1P = 0; // 1P 점수 초기화
    private int score2P = 0; // 2P 점수 초기화
    private JLabel label1P = new JLabel(); // 1P 점수 라벨 생성
    private JLabel label2P = new JLabel(); // 2P 점수 라벨 생성
    private JLabel combinationLabel = new JLabel(); // 가능한 조합 수를 표시할 라벨 생성
    private List<ShapePanel> selectedShapes = new ArrayList<>(); // 선택된 도형들을 저장할 리스트 생성
    private Set<String> selectedNumbers = new HashSet<>(); // 선택된 도형 번호를 추적할 셋 생성
    private Set<Set<String>> usedCombinations = new HashSet<>(); // 사용된 조합을 추적할 셋 생성
    private int initialPossibleCombinations = 0; // 초기 가능한 조합 수 초기화
    private int currentFoundCombinations = 0; // 현재 발견된 조합 수 초기화
    private Main mainFrame; // 메인 프레임 참조 변수

    // Combination 생성자
    public Combination(Main mainFrame) {
        this.mainFrame = mainFrame; // 메인 프레임 초기화
        setBackground(new Color(0x343434)); // 배경 색상 설정
        setLayout(new BorderLayout()); // 레이아웃 설정

        JPanel leftPanel = new JPanel(); // 왼쪽 패널 생성
        leftPanel.setPreferredSize(new Dimension(90, 800)); // 왼쪽 패널 크기 설정
        leftPanel.setBackground(new Color(0x343434)); // 왼쪽 패널 배경 색상 설정
        leftPanel.setLayout(new BorderLayout()); // 왼쪽 패널 레이아웃 설정
        textArea.setEditable(false); // 텍스트 영역 수정 불가 설정
        textArea.setFont(new Font("Arial", Font.BOLD, 24)); // 텍스트 영역 폰트 설정
        textArea.setForeground(Color.WHITE); // 텍스트 영역 글자 색상 설정
        textArea.setBackground(new Color(0x343434)); // 텍스트 영역 배경 색상 설정
        leftPanel.add(new JScrollPane(textArea), BorderLayout.CENTER); // 텍스트 영역을 스크롤 패널로 감싸고 왼쪽 패널에 추가

        JPanel centerPanel = new JPanel(); // 중앙 패널 생성
        centerPanel.setPreferredSize(new Dimension(243, 800)); // 중앙 패널 크기 설정
        centerPanel.setBackground(new Color(0x343434)); // 중앙 패널 배경 색상 설정
        centerPanel.setBorder(new LineBorder(Color.WHITE)); // 중앙 패널 테두리 설정
        centerPanel.setLayout(new GridBagLayout()); // 중앙 패널 레이아웃 설정

        GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints 객체 생성
        gbc.insets = new Insets(10, 0, 10, 0); // 간격 설정
        gbc.gridx = 0; // 그리드 x 좌표 설정
        gbc.gridy = 0; // 그리드 y 좌표 설정
        gbc.weighty = 1; // y 방향 가중치 설정
        gbc.anchor = GridBagConstraints.PAGE_START; // 컴포넌트 시작 위치 설정

        label1P.setText("1P: " + score1P); // 1P 점수 라벨 초기 텍스트 설정
        label1P.setFont(new Font("Arial", Font.BOLD, 20)); // 1P 점수 라벨 폰트 설정
        label1P.setForeground(Color.WHITE); // 1P 점수 라벨 글자 색상 설정
        centerPanel.add(label1P, gbc); // 1P 점수 라벨 중앙 패널에 추가

        gbc.gridy = 1; // 그리드 y 좌표 변경
        gbc.weighty = 0; // y 방향 가중치 변경
        gbc.anchor = GridBagConstraints.CENTER; // 컴포넌트 중앙 정렬

        JButton centerButton = new JButton("결!"); // 중앙 버튼 생성
        centerButton.setFont(new Font("HY견고딕", Font.BOLD, 20)); // 중앙 버튼 폰트 설정
        centerButton.setBackground(new Color(0xFFFFFF)); // 중앙 버튼 배경 색상 설정
        centerButton.setPreferredSize(new Dimension(220, 50)); // 중앙 버튼 크기 설정
        centerButton.setBorderPainted(false); // 중앙 버튼 테두리 페인팅 비활성화
        centerButton.setFocusPainted(false); // 중앙 버튼 포커스 페인팅 비활성화
        centerButton.addActionListener(new ActionListener() { // 버튼 클릭 리스너 추가
            @Override
            public void actionPerformed(ActionEvent e) {
                if (initialPossibleCombinations == currentFoundCombinations) { // 모든 조합을 찾았는지 확인
                    if (count % 2 == 0) { // 현재 차례가 2P인지 확인
                        score2P += 3; // 2P 점수 3점 추가
                        label2P.setText("2P: " + score2P); // 2P 점수 라벨 업데이트
                    } else { // 현재 차례가 1P인지 확인
                        score1P += 3; // 1P 점수 3점 추가
                        label1P.setText("1P: " + score1P); // 1P 점수 라벨 업데이트
                    }
                    showWinnerDialog(); // 승자 다이얼로그 표시
                } else {
                    if (count % 2 == 0) { // 현재 차례가 2P인지 확인
                        score2P--; // 2P 점수 1점 차감
                        label2P.setText("2P: " + score2P); // 2P 점수 라벨 업데이트
                    } else { // 현재 차례가 1P인지 확인
                        score1P--; // 1P 점수 1점 차감
                        label1P.setText("1P: " + score1P); // 1P 점수 라벨 업데이트
                    }
                }
                updateCombinationLabel(); // 가능한 조합 수 라벨 업데이트
            }
        });
        centerPanel.add(centerButton, gbc); // 중앙 버튼을 중앙 패널에 추가

        gbc.gridy = 2; // 그리드 y 좌표 변경
        gbc.weighty = 1; // y 방향 가중치 변경
        gbc.anchor = GridBagConstraints.PAGE_END; // 컴포넌트 끝 위치 설정

        label2P.setText("2P: " + score2P); // 2P 점수 라벨 초기 텍스트 설정
        label2P.setFont(new Font("Arial", Font.BOLD, 20)); // 2P 점수 라벨 폰트 설정
        label2P.setForeground(Color.WHITE); // 2P 점수 라벨 글자 색상 설정
        centerPanel.add(label2P, gbc); // 2P 점수 라벨 중앙 패널에 추가

        gbc.gridy = 3; // 그리드 y 좌표 변경
        gbc.anchor = GridBagConstraints.CENTER; // 컴포넌트 중앙 정렬
        initialPossibleCombinations = countPossibleCombinations(); // 초기 가능한 조합 수 계산
        combinationLabel.setText("조합가능한 개수: " + initialPossibleCombinations); // 조합 수 라벨 초기 텍스트 설정
        combinationLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20)); // 조합 수 라벨 폰트 설정
        combinationLabel.setForeground(Color.WHITE); // 조합 수 라벨 글자 색상 설정
        centerPanel.add(combinationLabel, gbc); // 조합 수 라벨 중앙 패널에 추가

        JPanel rightPanel = new JPanel(new BorderLayout()); // 오른쪽 패널 생성
        rightPanel.setPreferredSize(new Dimension(667, 800)); // 오른쪽 패널 크기 설정
        rightPanel.add(panel, BorderLayout.CENTER); // MyPanel 인스턴스를 오른쪽 패널에 추가
        rightPanel.setBackground(new Color(0x343434)); // 오른쪽 패널 배경 색상 설정

        mainPanel.add(leftPanel, BorderLayout.WEST); // 왼쪽 패널을 메인 패널에 추가
        mainPanel.add(centerPanel, BorderLayout.CENTER); // 중앙 패널을 메인 패널에 추가
        mainPanel.add(rightPanel, BorderLayout.EAST); // 오른쪽 패널을 메인 패널에 추가
        add(mainPanel); // 메인 패널을 Combination에 추가
    }

    private void appendNumber(String number) {
        count++; // 선택 횟수 증가
        textArea.append(number); // 번호 추가
        if (count % 3 == 0) { // 3의 배수일 때
            textArea.append("\n"); // 줄 바꿈
        } else {
            textArea.append(", "); // 콤마 추가
        }
    }

    private void checkAndIncreaseScore() {
        if (selectedShapes.size() == 3) { // 선택된 도형이 3개인지 확인
            Set<String> currentCombination = new HashSet<>(); // 현재 조합을 저장할 셋 생성
            for (ShapePanel sp : selectedShapes) { // 선택된 도형들 반복
                currentCombination.add(sp.getLabel()); // 도형의 라벨 추가
            }

            if (usedCombinations.contains(currentCombination)) { // 사용된 조합인지 확인
                deductScoreForCurrentPlayer(); // 중복된 조합이므로 점수 차감
            } else {
                usedCombinations.add(currentCombination); // 새로운 조합을 사용된 조합에 추가

                ShapePanel shape1 = selectedShapes.get(0); // 첫 번째 도형
                ShapePanel shape2 = selectedShapes.get(1); // 두 번째 도형
                ShapePanel shape3 = selectedShapes.get(2); // 세 번째 도형

                if (isValidCombination(shape1, shape2, shape3)) { // 유효한 조합인지 확인
                    currentFoundCombinations++; // 발견된 조합 수 증가
                    if (count % 2 == 0) { // 현재 차례가 2P인지 확인
                        score2P++; // 2P 점수 증가
                        label2P.setText("2P: " + score2P); // 2P 점수 라벨 업데이트
                    } else { // 현재 차례가 1P인지 확인
                        score1P++; // 1P 점수 증가
                        label1P.setText("1P: " + score1P); // 1P 점수 라벨 업데이트
                    }
                } else {
                    deductScoreForCurrentPlayer(); // 유효하지 않은 조합이므로 점수 차감
                }
            }

            selectedShapes.clear(); // 선택된 도형들 초기화
            selectedNumbers.clear(); // 선택된 번호들 초기화
        }
        updateCombinationLabel(); // 가능한 조합 수 라벨 업데이트
    }

    private void deductScoreForCurrentPlayer() {
        if (count % 2 == 0) { // 현재 차례가 2P인지 확인
            score2P--; // 2P 점수 차감
            label2P.setText("2P: " + score2P); // 2P 점수 라벨 업데이트
        } else { // 현재 차례가 1P인지 확인
            score1P--; // 1P 점수 차감
            label1P.setText("1P: " + score1P); // 1P 점수 라벨 업데이트
        }
    }

    private boolean isValidCombination(ShapePanel shape1, ShapePanel shape2, ShapePanel shape3) {
        boolean sameOrDifferentBgColor =
                (shape1.getBgColor().equals(shape2.getBgColor()) && shape2.getBgColor().equals(shape3.getBgColor())) ||
                        (!shape1.getBgColor().equals(shape2.getBgColor()) && !shape2.getBgColor().equals(shape3.getBgColor()) && !shape1.getBgColor().equals(shape3.getBgColor()));

        boolean sameOrDifferentShapes =
                (shape1.getShape().equals(shape2.getShape()) && shape2.getShape().equals(shape3.getShape())) ||
                        (!shape1.getShape().equals(shape2.getShape()) && !shape2.getShape().equals(shape3.getShape()) && !shape1.getShape().equals(shape3.getShape()));

        boolean sameOrDifferentShapeColors =
                (shape1.getShapeColor().equals(shape2.getShapeColor()) && shape2.getShapeColor().equals(shape3.getShapeColor())) ||
                        (!shape1.getShapeColor().equals(shape2.getShapeColor()) && !shape2.getShapeColor().equals(shape3.getShapeColor()) && !shape1.getShapeColor().equals(shape3.getShapeColor()));

        return sameOrDifferentBgColor && sameOrDifferentShapes && sameOrDifferentShapeColors;
    }

    private int countPossibleCombinations() {
        int count = 0; // 조합 개수 초기화
        List<ShapePanel> shapeList = panel.getShapePanels(); // 도형 리스트 가져오기
        for (int i = 0; i < shapeList.size(); i++) {
            for (int j = i + 1; j < shapeList.size(); j++) {
                for (int k = j + 1; k < shapeList.size(); k++) {
                    if (isValidCombination(shapeList.get(i), shapeList.get(j), shapeList.get(k))) {
                        count++; // 유효한 조합 발견 시 카운트 증가
                    }
                }
            }
        }
        return count; // 조합 개수 반환
    }

    private void updateCombinationLabel() {
        combinationLabel.setText("조합가능한 개수: " + countPossibleCombinations()); // 가능한 조합 수 라벨 업데이트
    }

    private void showWinnerDialog() {
        String winner = (score1P > score2P) ? "1P" : "2P"; // 승자 결정
        if (score1P == score2P) { // 동점일 경우
            winner = "Draw"; // 무승부로 설정
        }
        int response = JOptionPane.showOptionDialog(
                this,
                winner.equals("Draw") ? "It's a draw!" : winner + " wins!", // 결과 메시지 설정
                "Game Over", // 다이얼로그 제목
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Play Again", "Exit"}, // 옵션 버튼들
                "Play Again" // 기본 선택 버튼
        );
        if (response == 0) { // 다시 플레이 선택 시
            resetGame(); // 게임 리셋
        } else {
            mainFrame.showMainPanel(); // 메인 패널 표시
        }
    }

    private void resetGame() {
        score1P = 0; // 1P 점수 초기화
        score2P = 0; // 2P 점수 초기화
        label1P.setText("1P: " + score1P); // 1P 점수 라벨 초기화
        label2P.setText("2P: " + score2P); // 2P 점수 라벨 초기화
        count = 0; // 선택 횟수 초기화
        textArea.setText(""); // 텍스트 영역 초기화
        selectedShapes.clear(); // 선택된 도형들 초기화
        selectedNumbers.clear(); // 선택된 번호들 초기화
        usedCombinations.clear(); // 사용된 조합들 초기화
        panel.resetShapes(); // 도형 패널 초기화
        initialPossibleCombinations = countPossibleCombinations(); // 초기 가능한 조합 수 재계산
        currentFoundCombinations = 0; // 현재 발견된 조합 수 초기화
        updateCombinationLabel(); // 가능한 조합 수 라벨 업데이트
    }

    class MyPanel extends JPanel {
        private ShapePanel[] shapePanels = new ShapePanel[9]; // 9개의 ShapePanel 배열 생성
        private Color[] backgroundColors = {Color.WHITE, Color.GRAY, Color.BLACK}; // 배경 색상 배열
        private Color[] shapeColors = {Color.RED, Color.BLUE, Color.ORANGE}; // 도형 색상 배열
        private String[] shapes = {"CIRCLE", "TRIANGLE", "SQUARE"}; // 도형 모양 배열

        public MyPanel() {
            setBackground(new Color(0x343434)); // 배경 색상 설정
            setLayout(new GridLayout(3, 3, 20, 20)); // 그리드 레이아웃 설정 (3x3)
            resetShapes(); // 도형 패널 초기화
        }

        public void resetShapes() {
            removeAll(); // 모든 컴포넌트 제거
            Random random = new Random(); // 랜덤 객체 생성
            List<String> usedShapes = new ArrayList<>(); // 사용된 도형들을 저장할 리스트 생성
            for (int i = 0; i < 9; i++) { // 9개의 도형 생성
                Color bgColor = backgroundColors[random.nextInt(backgroundColors.length)]; // 랜덤 배경 색상 선택
                Color shapeColor = shapeColors[random.nextInt(shapeColors.length)]; // 랜덤 도형 색상 선택
                String shape = shapes[random.nextInt(shapes.length)]; // 랜덤 도형 모양 선택

                while (usedShapes.contains(shape + bgColor + shapeColor)) { // 중복된 도형 조합 방지
                    bgColor = backgroundColors[random.nextInt(backgroundColors.length)];
                    shapeColor = shapeColors[random.nextInt(shapeColors.length)];
                    shape = shapes[random.nextInt(shapes.length)];
                }

                usedShapes.add(shape + bgColor + shapeColor); // 사용된 도형 조합 추가

                JPanel container = new JPanel(new BorderLayout()); // 도형을 담을 컨테이너 패널 생성
                container.setBackground(new Color(0x343434)); // 컨테이너 배경 색상 설정
                JLabel label = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER); // 도형 번호 라벨 생성
                label.setFont(new Font("Arial", Font.BOLD, 20)); // 라벨 폰트 설정
                label.setForeground(Color.WHITE); // 라벨 글자 색상 설정
                label.setBackground(new Color(0x343434)); // 라벨 배경 색상 설정
                label.setOpaque(true); // 라벨 불투명 설정
                container.add(label, BorderLayout.NORTH); // 라벨을 컨테이너 상단에 추가
                shapePanels[i] = new ShapePanel(shape, bgColor, shapeColor, label.getText()); // ShapePanel 생성
                container.add(shapePanels[i], BorderLayout.CENTER); // ShapePanel을 컨테이너 중앙에 추가
                add(container); // 컨테이너를 MyPanel에 추가
            }
            revalidate(); // 레이아웃 다시 검증
            repaint(); // 다시 그리기
        }

        public List<ShapePanel> getShapePanels() {
            List<ShapePanel> list = new ArrayList<>(); // 도형 패널 리스트 생성
            for (ShapePanel sp : shapePanels) { // 모든 도형 패널 반복
                list.add(sp); // 리스트에 도형 패널 추가
            }
            return list; // 리스트 반환
        }
    }

    class ShapePanel extends JButton {
        private String shape; // 도형 모양
        private Color bgColor; // 배경 색상
        private Color shapeColor; // 도형 색상
        private String label; // 도형 라벨

        public ShapePanel(String shape, Color bgColor, Color shapeColor, String label) {
            this.shape = shape; // 도형 모양 초기화
            this.bgColor = bgColor; // 배경 색상 초기화
            this.shapeColor = shapeColor; // 도형 색상 초기화
            this.label = label; // 도형 라벨 초기화
            setBackground(bgColor); // 배경 색상 설정
            setPreferredSize(new Dimension(300, 200)); // 버튼 크기 설정
            addActionListener(new ActionListener() { // 버튼 클릭 리스너 추가
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedNumbers.contains(label)) { // 중복된 번호인지 확인
                        deductScoreForCurrentPlayer(); // 중복된 번호이므로 점수 차감
                        return; // 이미 선택된 도형이므로 무시
                    }

                    appendNumber(label); // 번호 추가
                    selectedShapes.add(ShapePanel.this); // 선택된 도형 리스트에 추가
                    selectedNumbers.add(label); // 선택된 번호 리스트에 추가
                    checkAndIncreaseScore(); // 점수 확인 및 증가
                }
            });
        }

        public String getShape() {
            return shape; // 도형 모양 반환
        }

        public Color getBgColor() {
            return bgColor; // 배경 색상 반환
        }

        public Color getShapeColor() {
            return shapeColor; // 도형 색상 반환
        }

        public String getLabel() {
            return label; // 도형 라벨 반환
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // 부모 클래스의 페인트 컴포넌트 호출
            g.setColor(shapeColor); // 그래픽 색상 설정

            int size = (int) (Math.min(getWidth(), getHeight()) * 0.6); // 도형 크기 계산
            int x = (getWidth() - size) / 2; // 도형 x 좌표 계산
            int y = (getHeight() - size) / 2; // 도형 y 좌표 계산

            switch (shape) { // 도형 모양에 따라 그리기
                case "CIRCLE":
                    g.fillOval(x, y, size, size); // 원 그리기
                    break;
                case "TRIANGLE":
                    int[] xPoints = {x + size / 2, x, x + size};
                    int[] yPoints = {y, y + size, y + size};
                    g.fillPolygon(xPoints, yPoints, 3); // 삼각형 그리기
                    break;
                case "SQUARE":
                    g.fillRect(x, y, size, size); // 사각형 그리기
                    break;
            }
        }
    }
}
