//package com.sprint.mission.discodeit.service.file;
//
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.service.ChannelService;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//
///*
//    리스트 통째로 파일에 저장
// */
//public class FileChannelService implements ChannelService {
//    static final String filePath = "src/main/java/";
//    static final String fileName = "channel.ser";
//
//    @Override
//    public Channel makeChannel(String channelName) {
//        List<Channel> channelList = findAllChannel();
//        Channel termChannel = new Channel(channelName);
//        if(channelList.contains(termChannel)) throw new IllegalArgumentException("해당 이름 채널 생성 불가");
//
//        channelList.add(termChannel);
//        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
//
//            objectOutputStream.writeObject(channelList);
//            return termChannel;
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
////    @Override
////    public Channel findChannelByName(String channelName) {
////        List<Channel> channelList = findAllChannel();
////        return channelList.stream()
////            .filter(channel -> channel.getChannelName().equals(channelName))
////            .findFirst()
////            .orElseThrow(() ->  new IllegalArgumentException("해당 이름의 채널을 찾을 수 없음"));
////    }
//
//    @Override
//    public Channel findChannelById(UUID channelId){
//        List<Channel> channelList = findAllChannel();
//        return channelList.stream()
//            .filter(channel -> channel.getId().equals(channelId))
//            .findFirst()
//            .orElseThrow(() -> new RuntimeException("서버 오류"));
//    }
//
//    /*
//        먼저 아이디로 채널을 찾는 메서드를 구현하고자 함
//        UUID 보다 그냥 이름 고유하게 설정해서 이름으로 찾는것으로 수정
//        근데 업데이트 메서드를 작성하기 위해 파인드메서드를 쓰려했는데 여기서 다시 UUID릁 통해 찾으려하니 뭔가 꼬임
//        굳이 UUID 가 필요한가부터 / 어떻게 해야할까요
//     */
////    @Override
////    public Channel updateChannelName(UUID channelId, String newName) {
////        Channel channel = findChannelById(channelId);
////        if(channel.getId().equals(channelId)){
////            channel.setChannelName(newName);
////            return channel;
////        }
////
////        throw new RuntimeException("업데이트 실패");
////    }
//
//    @Override
//    public void deleteChannel(UUID channelId) {
//
//    }
//
//    @Override
//    public List<Channel> findAllChannel(){
//        File file = new File(filePath + fileName);
//
//        if(!file.exists()) return new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(file);
//            ObjectInputStream ois = new ObjectInputStream(fis)) {
//            return (List<Channel>) ois.readObject();
//        } catch (FileNotFoundException e) {
//            return new ArrayList<>();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//}
