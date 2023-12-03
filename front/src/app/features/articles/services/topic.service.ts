import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private pathService = 'api/topic';

  constructor(private httpClient: HttpClient) {}

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

  public subscribeTopic(id: number): void {
    this.httpClient
      .post(`${this.pathService}/${id}/subscription`, '', {
        responseType: 'text',
      })
      .pipe(take(1))
      .subscribe();
  }

  public unsubscribeTopic(id: number): void {
    this.httpClient
      .delete(`${this.pathService}/${id}/subscription`, {
        responseType: 'text',
      })
      .pipe(take(1))
      .subscribe();
  }
}
