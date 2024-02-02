import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs';
import { Article } from 'src/app/features/articles/interfaces/article.interface';
import { MessageDto } from '../../interfaces/message-dto';
import { ArticlesService } from '../../services/articles.service';
import { MessagesService } from '../../services/messages.service';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { NgIf, NgFor, DatePipe } from '@angular/common';
import { FlexModule } from '@angular/flex-layout/flex';

@Component({
    selector: 'app-detail',
    templateUrl: './detail.component.html',
    styleUrls: ['./detail.component.scss'],
    standalone: true,
    imports: [
        FlexModule,
        NgIf,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        NgFor,
        FormsModule,
        ReactiveFormsModule,
        MatInputModule,
        DatePipe,
    ],
})
export class DetailComponent implements OnInit {
  public messageForm!: FormGroup;
  public article: Article | undefined;
  public comments!: Array<MessageDto>;
  private id!: string;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messagesService: MessagesService,
    private articlesService: ArticlesService
  ) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;

    this.articlesService
      .detail(this.id)
      .pipe(take(1))
      .subscribe((article: Article) => (this.article = article));

    this.articlesService
      .comments(this.id)
      .pipe(take(1))
      .subscribe((comments: MessageDto[]) => (this.comments = comments));
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    this.messagesService
      .send(this.article!.id, this.messageForm.value.message)
      .subscribe(() => {
        this.initMessageForm();
        this.articlesService
          .comments(this.id)
          .pipe(take(1))
          .subscribe((comments: MessageDto[]) => (this.comments = comments));
      });
  }

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(500),
        ],
      ],
    });
  }
}
