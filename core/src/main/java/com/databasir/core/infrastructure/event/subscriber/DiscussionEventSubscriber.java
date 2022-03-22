package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.domain.discussion.event.DiscussionCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscussionEventSubscriber {

    @EventListener(classes = DiscussionCreated.class)
    public void onDiscussionCreated(DiscussionCreated created) {
        // TODO notification group member who subscribe this event
    }
}
