import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from '../../services/articles.service';
import { take } from 'rxjs';
import { TopicService } from '../../services/topic.service';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { NgFor, AsyncPipe } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FlexModule } from '@angular/flex-layout/flex';
import { MatCardModule } from '@angular/material/card';

@Component({
    selector: 'app-form',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss'],
    standalone: true,
    imports: [
        MatCardModule,
        FlexModule,
        MatButtonModule,
        RouterLink,
        MatIconModule,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatSelectModule,
        NgFor,
        MatOptionModule,
        MatInputModule,
        AsyncPipe,
    ],
})
export class FormComponent implements OnInit {
  public articleForm!: FormGroup;
  public topics$ = this.topicService.all();

  public form = this.fb.group({
    topic: ['', [Validators.required]],
    title: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(60)],
    ],
    content: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(500)],
    ],
  });

  constructor(
    private fb: FormBuilder,
    private articlesService: ArticlesService,
    private topicService: TopicService,
    private router: Router
  ) {}

  public ngOnInit(): void {
    this.initForm();
  }

  public submit(): void {
    const article = this.form.value as Article;
    this.articlesService
      .create(this.form.value.topic!, article)
      .pipe(take(1))
      .subscribe(() => this.router.navigate(['articles']));
  }

  private initForm(article?: Article): void {
    this.articleForm = this.fb.group({
      description: [article ? article.content : '', [Validators.required]],
    });
  }
}
