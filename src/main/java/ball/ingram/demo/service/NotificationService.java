package ball.ingram.demo.service;

import ball.ingram.demo.dto.NotificationDTO;
import ball.ingram.demo.dto.PaginationDTO;
import ball.ingram.demo.enums.NotificationStatusEnum;
import ball.ingram.demo.enums.NotificationTypeEnum;
import ball.ingram.demo.exception.CustomizeErrorCode;
import ball.ingram.demo.exception.CustomizeException;
import ball.ingram.demo.mapper.NotificationMapper;
import ball.ingram.demo.model.Notification;
import ball.ingram.demo.model.NotificationExample;
import ball.ingram.demo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        NotificationExample NotificationExample = new NotificationExample();
        NotificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(NotificationExample);
        Integer totalPage;
        if( totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = (totalCount / size) + 1;
        }

        if(page < 1){
            page =1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page -  1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId)
                ;
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        if(notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }
    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long unreadCount = notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(), user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
