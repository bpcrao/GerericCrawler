package pramati.crawler.impl.mailcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import pramati.crawler.interfaces.UrlFilter;
import pramati.crawler.interfaces.WebCrawlerImp;
import pramati.crawler.processor.UrlCrawler;

public class MailCrawlerStartup implements WebCrawlerImp,ApplicationContextAware{
	private ApplicationContext context;
	private URL url;
	private static String year="";
	
	public void startCrawling(String[] input) throws Exception {
		System.out.println("starting crawler....");
		this.validateInput(input);
		UrlCrawler urlCrawler=(UrlCrawler) context.getBean("urlcrawler");
		urlCrawler.startUrlCrawling(this.url,URL_FILTER_FOR_MAIL);
	}
	private void validateInput(String[] input) throws MalformedURLException {
		try {
			url=new URL(input[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		}
		try{
		Integer.parseInt(input[1]);
		year=input[1];
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}
	private static final UrlFilter URL_FILTER_FOR_MAIL =new UrlFilter(){

		public Set<URL> filter(List<URL> urlList) {
			String regex=year+"([0-9])([0-9]?)"+".mbox";
			Set<URL> filteredUrlSet=new HashSet<URL>();
			for(URL url:urlList){
				Pattern pattern=Pattern.compile(year+"([0-9])([0-9]?)"+".mbox");
				Matcher matcher=pattern.matcher(url.toString());
				if (matcher.find()
						&& !((url.toString()).contains(".mbox/date"))
						&& !((url.toString()).contains(".mbox/author"))
						&& !((url.toString()).contains(".mbox/browser"))) {
					filteredUrlSet.add(url);
				}
			}
			
			return filteredUrlSet;
		}

		public boolean isfinal(URL url) {
			if(url.toString().contains("raw")){
				return true;
			}else{
				return false;
			}
		}	
	};
}

