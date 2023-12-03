import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic.service';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/interfaces/user.interface';
import { Topic } from '../../interfaces/topic.interface';
import { UserService } from 'src/app/services/user.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss'],
})
export class TopicComponent implements OnInit {
  public topics$ = this.topicService.all();
  public userSubscriptions!: Array<Topic>;

  constructor(
    private sessionService: SessionService,
    private topicService: TopicService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService
      .getUserById(this.user!.id)
      .pipe(take(1))
      .subscribe((user) => (this.userSubscriptions = user.subscriptions));
  }

  get user(): User | undefined {
    return this.sessionService.user;
  }

  public includes(topic: Topic): boolean {
    return this.userSubscriptions?.some((e) => e.id === topic.id);
  }

  public subscribeTopic(topic: Topic): void {
    this.topicService.subscribeTopic(topic.id);
    this.userSubscriptions.push(topic);
  }

  public unsubscribeTopic(topic: Topic): void {
    this.topicService.unsubscribeTopic(topic.id);
    this.userSubscriptions = this.userSubscriptions.filter(
      (e) => e.id !== topic.id
    );
  }
}
