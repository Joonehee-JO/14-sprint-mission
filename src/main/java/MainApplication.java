import controller.user.UserController;
import controller.user.UserDTO;
import global.config.Config;
import global.exception.CustomException;
import java.util.Scanner;

public class MainApplication {
    static Scanner sc = new Scanner(System.in);
    static Config config = new Config();

    /*
        생성된 유저 / 채널 번호 메모리에 갖기 위한 필드
     */
    static private Long userId = null;
    static private Long channelId = null;

    public static void main(String[] args) {
        String userInput;

        do {
            System.out.println("========== welcome !! ==========");
            System.out.println("\tYou can choose menu");
            System.out.println("\t 1. init your account");
            System.out.println("\t 2. going channel");
            System.out.println("\t 3. exit");

            userInput = sc.nextLine().trim();

            switch (userInput){
                case "1" :
                    initMenu();
                    break;
                case "2" :
                    break;
                case "3" :
                    System.out.println("프로그램 종료");
                    break;
                default:
                    System.out.println("제대로 입력해주세요");
                    break;
            }
        } while (!userInput.equals(String.valueOf(3)));
    }

    static void initMenu(){
        System.out.println(" ======= Init Account Menu =======");
        System.out.println("\t please input your name");
        String name = sc.nextLine();
        try{
            UserDTO userDTO = new UserDTO(name);
            userId = config.getUserController().initUser(userDTO);
        } catch (CustomException e){
            System.out.println(e.getCustomErrorCode().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
