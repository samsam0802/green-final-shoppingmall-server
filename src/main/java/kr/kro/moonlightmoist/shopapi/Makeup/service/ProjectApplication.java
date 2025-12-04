//package kr.kro.moonlightmoist.shopapi.Makeup.service;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ProjectApplication implements CommandLineRunner {
//
//    private final MakeupImportService importService;
//
//    public ProjectApplication(MakeupImportService importService) {
//        this.importService = importService;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ProjectApplication.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        importService.importProducts(); // 서버 시작 시 자동 실행
//    }
//}
