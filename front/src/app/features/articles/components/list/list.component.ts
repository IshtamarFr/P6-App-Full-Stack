import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { ArticlesService } from '../../services/articles.service';
import { MatCardModule } from '@angular/material/card';
import { NgFor, NgIf, AsyncPipe, DatePipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { FlexModule } from '@angular/flex-layout/flex';

@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.scss'],
    standalone: true,
    imports: [
        FlexModule,
        MatButtonModule,
        RouterLink,
        MatIconModule,
        NgFor,
        MatCardModule,
        NgIf,
        AsyncPipe,
        DatePipe,
    ],
})
export class ListComponent {
  public articles$ = this.articlesService.all(this.user!.id);

  constructor(
    private sessionService: SessionService,
    private articlesService: ArticlesService
  ) {}

  get user(): User | undefined {
    return this.sessionService.user;
  }
}
